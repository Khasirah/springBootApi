package com.peppo.springbootapi.service;

import com.peppo.springbootapi.entity.User;
import com.peppo.springbootapi.model.LoginUserRequest;
import com.peppo.springbootapi.model.TokenResponse;
import com.peppo.springbootapi.repository.UserRepository;
import com.peppo.springbootapi.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TokenResponse login(LoginUserRequest loginUserRequest) {
        validationService.validate(loginUserRequest);

        User user = userRepository.findById(loginUserRequest.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username or password"));

        if (BCrypt.checkpw(loginUserRequest.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30Days());
            userRepository.save(user);

            return TokenResponse.builder()
                    .token(user.getToken())
                    .expiredAt(user.getTokenExpiredAt())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid username or password");
        }
    }

    private Long next30Days() {
        return System.currentTimeMillis() + (1000 * 60 * 24 * 30);
    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

}
