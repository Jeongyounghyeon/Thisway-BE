package org.thisway.auth.domain;

public interface AuthService {

    public AuthInfo.LoginResponse login(AuthCommand.LoginRequest request);

}
