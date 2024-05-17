package com.swd392.funfundbe.security;

import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.repository.UserRepository;
import com.swd392.funfundbe.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) {
        UserTbl user = userService.getUserByEmail(email);
        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(user.getRole().getRoleId()));
        return new UserDetailsImpl(user, authorities);
    }


}
