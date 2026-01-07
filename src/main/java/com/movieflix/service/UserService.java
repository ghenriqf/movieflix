package com.movieflix.service;

import com.movieflix.controller.request.UserRequest;
import com.movieflix.controller.response.UserResponse;
import com.movieflix.entity.User;
import com.movieflix.mapper.UserMapper;
import com.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse save (UserRequest request) {
        String password = request.password();

        User entity = UserMapper.toEntity(request);
        entity.setPassword(passwordEncoder.encode(password));

        User save = userRepository.save(entity);
        return UserMapper.toResponse(save);
    }
}
