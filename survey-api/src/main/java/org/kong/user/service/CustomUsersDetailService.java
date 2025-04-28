package org.kong.user.service;

import lombok.RequiredArgsConstructor;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.user.dto.CustomUserDetails;
import org.kong.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByUserName(username).orElse(null));
    }
}
