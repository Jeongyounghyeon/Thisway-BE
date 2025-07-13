package org.thisway.auth.domain;

import org.thisway.member.domain.Member;

public interface TokenGenerator {

    AuthInfo.LoginResponse generator(Member member);

}
