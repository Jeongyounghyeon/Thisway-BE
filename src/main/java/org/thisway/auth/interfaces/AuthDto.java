package org.thisway.auth.interfaces;

import org.thisway.auth.domain.AuthCommand;

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
}
