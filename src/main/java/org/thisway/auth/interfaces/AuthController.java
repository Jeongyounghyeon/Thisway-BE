package org.thisway.auth.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thisway.auth.application.AuthFacade;
import org.thisway.auth.domain.AuthInfo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/login")
    public ResponseEntity<AuthInfo.LoginResponse> login(
            @Valid
            @RequestBody
            AuthDto.LoginRequest request
    ) {
        AuthInfo.LoginResponse response = authFacade.login(request.toCommand());
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
