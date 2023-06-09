package com.swd392.funfundbe.service;

import com.swd392.funfundbe.model.entity.RoleTbl;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.LoginStatus;
import com.swd392.funfundbe.model.enums.Role;
import com.swd392.funfundbe.repository.RoleRepository;
import com.swd392.funfundbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserTbl getUserByEmailOrPhone(String username) {
        return userRepository.findByEmailOrPhone(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email or Phone number not found"));
    }

    public void createBasicUserViaGoogle(String email) {
        RoleTbl PORole = roleRepository.findById(Role.PO.toString()).get();
        UserTbl newUser = new UserTbl();
        newUser.setEmail(email);
        newUser.setRole(PORole);
        newUser.setCreatedAt(new Date());
        newUser.setStatus(LoginStatus.FILLING_REQUIRED);
        newUser.setEnabled(false);
        userRepository.save(newUser);
    }

    public void createBasicUserViaPhone(String phone) {
        RoleTbl INVRole = roleRepository.findById(Role.INVESTOR.toString()).get();
        UserTbl newUser = new UserTbl();
        newUser.setPhone(phone);
        newUser.setRole(INVRole);
        newUser.setCreatedAt(new Date());
        newUser.setStatus(LoginStatus.FILLING_REQUIRED);
        newUser.setEnabled(false);
        userRepository.save(newUser);
    }
}
