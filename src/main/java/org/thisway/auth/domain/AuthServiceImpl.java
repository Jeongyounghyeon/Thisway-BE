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
    public AuthInfo.LoginResponse login(AuthCommand.LoginRequest request) {

        Member member = memberReader.findByEmailAndActiveTrue(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword()))
            throw new CustomException(ErrorCode.AUTH_PASSWORD_NOT_MATCH);

        return tokenGenerator.generator(member);
    }
}
