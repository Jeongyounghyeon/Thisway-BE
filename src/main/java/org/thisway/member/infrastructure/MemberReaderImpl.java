package org.thisway.member.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.thisway.member.domain.Member;
import org.thisway.member.domain.MemberReader;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findByEmailAndActiveTrue(String email) {
        return memberRepository.findByEmailAndActiveTrue(email);
    }
}
