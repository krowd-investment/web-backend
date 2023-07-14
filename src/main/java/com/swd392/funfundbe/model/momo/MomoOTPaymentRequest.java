package com.swd392.funfundbe.model.momo;

import com.swd392.funfundbe.config.MomoConfig;
import com.swd392.funfundbe.model.enums.Platform;
import com.swd392.funfundbe.utils.MomoUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MomoOTPaymentRequest implements Serializable {
    private String partnerCode;
    private String requestId;
    private Long amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String requestType;
    private String extraData;
    private String lang;
    private String signature;

    public MomoOTPaymentRequest(String platform, Long amount, String requestId, String orderId, MomoConfig momoConfig) {
        this.partnerCode = momoConfig.partnerCode;
        this.requestId = requestId;
        this.amount = amount;
        this.orderId = orderId;
        this.orderInfo = momoConfig.orderInfo;
        this.redirectUrl = platform.equalsIgnoreCase(Platform.WEB.toString()) ? momoConfig.webRedirectUrl : momoConfig.mobileRedirectUrl;
        this.ipnUrl = momoConfig.ipnUrl;
        this.requestType = momoConfig.requestType;
        this.extraData = momoConfig.extraData;
        this.lang = momoConfig.lang;
        makeSignature(momoConfig.accessKey, momoConfig.secretKey);
    }

    public void makeSignature(String accessKey, String secretKey) {
        String rawHash = "accessKey=" + accessKey + "&amount=" + this.amount + "&extraData=" + this.extraData
                + "&ipnUrl=" + this.ipnUrl + "&orderId=" + this.orderId + "&orderInfo=" + this.orderInfo
                + "&partnerCode=" + this.partnerCode + "&redirectUrl=" + this.redirectUrl
                + "&requestId=" + this.requestId + "&requestType=" + this.requestType;
        this.signature = MomoUtil.hmacSHA256(rawHash, secretKey);
    }
}
