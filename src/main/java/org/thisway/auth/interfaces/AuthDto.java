package org.thisway.auth.interfaces;

import org.thisway.auth.application.AuthCommand;
import org.thisway.auth.application.AuthInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

public class AuthDto {

    @Value
    @Builder
    @Jacksonized
    public static class LoginRequest {

        @NotBlank
        private String email;

        @NotBlank
        private String password;

        public AuthCommand.LoginRequest toCommand() {
            return AuthCommand.LoginRequest.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }

    @Value
    @Builder
    public static class LoginResponse {

        private final String token;
        private final String refreshToken;

        public static LoginResponse from(AuthInfo.LoginResult info) {
            return LoginResponse.builder()
                    .token(info.getAccessToken())
                    .refreshToken(info.getRefreshToken())
                    .build();
        }
    }
}
