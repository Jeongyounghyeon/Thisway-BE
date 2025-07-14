package org.thisway.auth.application;

import org.springframework.stereotype.Service;
import org.thisway.auth.domain.AuthService;
import org.thisway.auth.domain.AuthToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    public AuthInfo.LoginResult login(AuthCommand.LoginRequest request) {
        AuthToken tokens = authService.login(request.getEmail(), request.getPassword());
        return AuthInfo.LoginResult.from(tokens);
    }
}
