package org.thisway.auth.domain;

public interface AuthService {

    public AuthToken login(String email, String password);

}
