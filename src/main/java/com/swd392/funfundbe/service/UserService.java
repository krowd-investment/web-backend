package com.swd392.funfundbe.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomForbiddenException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
import com.swd392.funfundbe.model.Response.PersonalWalletResponse;
import com.swd392.funfundbe.model.entity.PersonalWallet;
import com.swd392.funfundbe.model.entity.WalletType;
import com.swd392.funfundbe.model.enums.WalletTypeString;
import com.swd392.funfundbe.repository.PersonalWalletRepository;
import com.swd392.funfundbe.repository.WalletTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swd392.funfundbe.model.Request.RegisterUserRequest;
import com.swd392.funfundbe.model.Response.UserResponse;
import com.swd392.funfundbe.model.entity.RoleTbl;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.LoginStatus;
import com.swd392.funfundbe.model.enums.Role;
import com.swd392.funfundbe.repository.RoleRepository;
import com.swd392.funfundbe.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final WalletTypeRepository walletTypeRepository;
    private final PersonalWalletRepository personalWalletRepository;

    public UserTbl getUserByEmail(String email) {
        Optional<UserTbl> isExistUser = userRepository.findByEmail(email);
        return isExistUser.orElseGet(() -> createBasicUserViaGoogle(email));
    }

    public UserTbl createBasicUserViaGoogle(String email) {
        RoleTbl INVRole = roleRepository.findById(Role.INVESTOR.toString()).get();
        UserTbl newUser = new UserTbl();
        newUser.setEmail(email);
        newUser.setRole(INVRole);
        newUser.setCreatedAt(new Date());
        newUser.setStatus(LoginStatus.FILLING_REQUIRED);
        newUser.setEnabled(false);
        return userRepository.save(newUser);
    }

    // public void createBasicUserViaPhone(String phone) {
    // RoleTbl INVRole = roleRepository.findById(Role.INVESTOR.toString()).get();
    // UserTbl newUser = new UserTbl();
    // newUser.setPhone(phone);
    // newUser.setRole(INVRole);
    // newUser.setCreatedAt(new Date());
    // newUser.setStatus(LoginStatus.FILLING_REQUIRED);
    // newUser.setEnabled(false);
    // userRepository.save(newUser);
    // }

    public UserResponse registerUser(RegisterUserRequest request) throws CustomBadRequestException, CustomNotFoundException {
        UserTbl currentUser = AuthenticateService.getCurrentUserFromSecurityContext();
        if (currentUser.getStatus().equals(LoginStatus.APPROVED))
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Current user has been registered").build()
            );
        if (currentUser.getStatus().equals(LoginStatus.DELETED))
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Current user has been banned").build()
            );
        if (currentUser.getStatus().equals(LoginStatus.PENDING))
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Current user is waiting for admin approval").build()
            );

        if (request.getRoleId().equals(Role.ADMIN))
            throw new CustomBadRequestException(
                    CustomError.builder().code("400").message("Role is not valid for registering").build()
            );

        UserTbl user = userRepository.findById(currentUser.getUserId()).get();
        RoleTbl role = roleRepository.findById(request.getRoleId().toString())
                .orElseThrow(() -> new CustomBadRequestException(
                        CustomError.builder().code("400").message("Role not found").build()
                ));
        user.setRole(role);
        user.setFull_name(request.getFullName().toUpperCase());
        user.setPhone(request.getPhone());
        user.setAvatar(request.getAvatar());
        user.setId_card(request.getId_card());
        user.setGender(request.getGender().toString());
        user.setBirthdate(request.getBirthdate());
        user.setTaxIdentification(request.getTaxIdentification());
        user.setAddress(request.getAddress());
        user.setBankName(request.getBankName());
        user.setBank_account(request.getBankAccount());
        user.setMomo(request.getMomo());

        if (role.getRoleId().equals(Role.INVESTOR.toString())) {
            user.setStatus(LoginStatus.APPROVED);
            user.setEnabled(true);
        } else {
            user.setStatus(LoginStatus.PENDING);
        }

        // Bỏ thông tin vào bảng investor hoặc po tương ứng

        if (user.getRole().getRoleId().equals(Role.INVESTOR.toString())) {
            createWallet(user, WalletTypeString.GENERAL_WALLET);
            createWallet(user, WalletTypeString.COLLECTION_WALLET);
        }

        return new UserResponse(user);
    }

    public void createWallet(UserTbl user, WalletTypeString walletTypeString) throws CustomNotFoundException {
        WalletType walletType = walletTypeRepository.findById(walletTypeString.toString())
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder()
                                .code("404").message("General wallet type ID not found")
                                .build())
                );
        PersonalWallet wallet = PersonalWallet.builder()
                .walletId(UUID.randomUUID())
                .personalwalletOf(user)
                .walletType(walletType)
                .balance(BigDecimal.ZERO)
                .createdAt(new Date())
                .build();
        personalWalletRepository.save(wallet);
    }


    public List<UserResponse> getAllUsers() {
        List<UserTbl> users = userRepository.findAll();
        return users.stream().map(UserResponse::new).toList();
    }

    public UserResponse getCurrentUserInfo() throws CustomNotFoundException, CustomForbiddenException {
        boolean checkCurrentUser = AuthenticateService.checkCurrentUser();
        if (!checkCurrentUser) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        return new UserResponse(AuthenticateService.getCurrentUserFromSecurityContext());
    }

    public UserResponse updateCurrentUser(RegisterUserRequest request) throws CustomForbiddenException, CustomNotFoundException {
        boolean checkCurrentUser = AuthenticateService.checkCurrentUser();
        if (!checkCurrentUser) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }

        UserTbl currentUser = userRepository.findById(AuthenticateService.getCurrentUserFromSecurityContext().getUserId()).get();
        currentUser.setFull_name(request.getFullName());
        currentUser.setPhone(request.getPhone());
        currentUser.setAvatar(request.getAvatar());
        currentUser.setId_card(request.getId_card());
        currentUser.setGender(request.getGender().toString());
        currentUser.setBirthdate(request.getBirthdate());
        currentUser.setTaxIdentification(request.getTaxIdentification());
        currentUser.setAddress(request.getAddress());
        currentUser.setBankName(request.getBankName());
        currentUser.setBank_account(request.getBankAccount());
        currentUser.setMomo(request.getMomo());

        return new UserResponse(currentUser);
    }

    public PersonalWalletResponse getPersonalWallet(WalletTypeString walletTypeString) throws CustomForbiddenException, CustomNotFoundException {
        boolean checkCurrentUser = AuthenticateService.checkCurrentUser();
        if (!checkCurrentUser) {
            throw new CustomForbiddenException(
                    CustomError.builder().code("403").message("can't access this api").field("user_status").build());
        }
        PersonalWallet personalWallet = getPersonalWallet(
                AuthenticateService.getCurrentUserFromSecurityContext(),
                walletTypeString
        );
        return new PersonalWalletResponse(personalWallet);
    }


    public PersonalWallet getPersonalWallet(UserTbl user, WalletTypeString walletTypeString) throws CustomNotFoundException {
        PersonalWallet personalWallet = personalWalletRepository.findByPersonalwalletOf_UserIdAndWalletType_WalletTypeId(
                user.getUserId(),
                walletTypeString.toString()
        )
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder()
                                .code("404").message("Personal wallet not found")
                                .build()
                ));
        return personalWallet;
    }

}
