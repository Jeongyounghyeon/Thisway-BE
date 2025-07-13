package org.thisway.auth.application;

import org.springframework.stereotype.Service;
import org.thisway.auth.domain.AuthCommand;
import org.thisway.auth.domain.AuthInfo;
import org.thisway.auth.domain.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;

    public AuthInfo.LoginResponse login(AuthCommand.LoginRequest request) {
        return authService.login(request);
    }
}
