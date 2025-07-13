package org.thisway.auth.domain;

import lombok.Builder;
import lombok.Value;

public class AuthInfo {

    @Value
    @Builder
    public static class LoginResponse {

        private final String token;
        private final String refreshToken;
    }
}
