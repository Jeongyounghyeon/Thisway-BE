package org.thisway.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thisway.member.domain.Member;
import org.thisway.member.domain.MemberReader;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberReader memberReader;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    @Override
    public AuthToken login(String email, String password) {

        Member member = memberReader.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(password, member.getPassword()))
            throw new CustomException(ErrorCode.AUTH_PASSWORD_NOT_MATCH);

        return tokenGenerator.generator(member);
    }
}
