package org.kong.user.service;

import lombok.RequiredArgsConstructor;
import org.kong.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
