package com.swd392.funfundbe.service;

import com.swd392.funfundbe.config.MomoConfig;
import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.entity.DepositTransaction;
import com.swd392.funfundbe.model.entity.PersonalWallet;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.Platform;
import com.swd392.funfundbe.model.enums.Role;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.model.momo.MomoOTPaymentRequest;
import com.swd392.funfundbe.model.momo.MomoPaymentResult;
import com.swd392.funfundbe.repository.DepositTransactionRepository;
import com.swd392.funfundbe.repository.PersonalWalletRepository;
import com.swd392.funfundbe.utils.MomoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

    private final MomoConfig momoConfig;
    private final PersonalWalletRepository personalWalletRepository;
    private final DepositTransactionRepository depositTransactionRepository;

    public Object depositToGeneralWallet(String platform, BigDecimal amount) throws CustomNotFoundException, CustomForbiddenException, CustomBadRequestException {
        UserTbl currentUser = AuthenticateService.getCurrentUserFromSecurityContext();
        if (!currentUser.isEnabled()) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("Can't access this api").field("user_status").build());
        }
        if (currentUser.getRole().getRoleId().equals(Role.ADMIN.toString()))
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("Admin cannot deposit").build());
        if (!platform.equals(Platform.WEB.toString()) && !platform.equals(Platform.MOBILE.toString())) {
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Invalid platform for payment").build()
            );
        }
        String requestId = MomoUtil.generateRequestId();
        String orderId = MomoUtil.generateOrderId();
        makeDepositTransaction(currentUser, amount, orderId);
        return createMomoPayment(platform, amount, requestId, orderId);
    }

    private void makeDepositTransaction(UserTbl user, BigDecimal amount, String orderId) throws CustomNotFoundException {
        PersonalWallet generalWallet = personalWalletRepository.findByPersonalwalletOf_UserIdAndWalletType_WalletTypeId(
                        user.getUserId(),
                        WalletTypeString.GENERAL_WALLET.toString()
                )
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder()
                                .code("404").message("Personal wallet not found")
                                .build()
                ));
        DepositTransaction depositTransaction = DepositTransaction.builder()
                .toWallet(generalWallet)
                .createdBy(user)
                .createdAt(new Date())
                .amount(amount)
                .orderId(orderId)
                .build();
        depositTransactionRepository.save(depositTransaction);
    }

    private Object createMomoPayment(String platform, BigDecimal amount, String requestId, String orderId) {
        MomoOTPaymentRequest request = new MomoOTPaymentRequest(platform, amount.longValue(), requestId, orderId, momoConfig);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MomoOTPaymentRequest> requestEntity = new HttpEntity<>(request, headers);
        String url = momoConfig.paymentUrl;
        Object momoResponse = restTemplate.postForObject(url, requestEntity, Object.class);
        return momoResponse;
    }

    public void verifyDepositTransaction(MomoPaymentResult result) throws CustomNotFoundException, CustomForbiddenException {
        DepositTransaction depositTransaction = depositTransactionRepository.findByOrderIdAndVerifiedFalse(result.getOrderId())
                .orElseThrow(() -> new CustomNotFoundException(CustomError.builder()
                        .code("404").message("Order ID not found")
                        .build()
        ));
        log.info(result.toString());
        String verifySignature = makeVerifySignature(
                result.getOrderId(),
                result.getRequestId(),
                result.getAmount(),
                result.getOrderType(),
                result.getTransId(),
                result.getResultCode(),
                result.getMessage(),
                result.getPayType(),
                result.getResponseTime()
        );
        if (!verifySignature.equals(result.getSignature()))
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("Invalid signature").field("user_status").build());

        depositTransaction.setVerified(true);
        BigDecimal currentBalance = depositTransaction.getToWallet().getBalance();
        depositTransaction.getToWallet().setBalance(currentBalance.add(depositTransaction.getAmount()));
    }

    private String makeVerifySignature(
            String orderId,
            String requestId,
            Long amount,
            String orderType,
            Long transId,
            Integer resultCode,
            String message,
            String payType,
            Long responseTime
    ) {
        String rawHash = "accessKey=" + momoConfig.accessKey
                + "&amount=" + amount + "&extraData=" + momoConfig.extraData
                + "&message=" + message + "&orderId=" + orderId
                + "&orderInfo=" + momoConfig.orderInfo + "&orderType=" + orderType
                + "&partnerCode=" + momoConfig.partnerCode + "&payType=" + payType
                + "&requestId=" + requestId + "&responseTime=" + responseTime
                + "&resultCode=" + resultCode + "&transId=" + transId;
        return MomoUtil.hmacSHA256(rawHash, momoConfig.secretKey);
    }
}
