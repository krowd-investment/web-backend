package com.swd392.funfundbe.service;

import com.swd392.funfundbe.model.entity.RoleTbl;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.LoginStatus;
import com.swd392.funfundbe.repository.RoleRepository;
import com.swd392.funfundbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {
    private final String ROLE_PO = "PO";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserTbl getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    public void createBasicUserViaGoogle(String email) {
        RoleTbl PORole = roleRepository.findById(ROLE_PO).get();
        UserTbl newUser = new UserTbl();
        newUser.setEmail(email);
        newUser.setRole(PORole);
        newUser.setCreatedAt(new Date());
        newUser.setStatus(LoginStatus.FILLING_REQUIRED);
        newUser.setEnabled(false);
        userRepository.save(newUser);
    }
}
