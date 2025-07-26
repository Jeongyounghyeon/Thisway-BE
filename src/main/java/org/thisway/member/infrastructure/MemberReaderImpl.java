package org.thisway.member.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thisway.member.domain.Member;
import org.thisway.member.domain.MemberQuery;
import org.thisway.member.domain.MemberReader;
import org.thisway.member.domain.MemberRole;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    @Override
    public Member getMember(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public Page<Member> getMembers(Collection<MemberRole> accessibleRole, Pageable pageable) {
        return memberRepository.findAllByRoleIn(accessibleRole, pageable);
    }

    @Override
    public Page<Member> getMembers(MemberQuery.SearchMember memberQuery, Pageable pageable) {
        return memberRepository.searchMembers(
                memberQuery.getRoles(),
                memberQuery.getCompany(),
                memberQuery.getName(),
                pageable
        );
    }

    @Override
    public long countMember(MemberQuery.SearchMember memberQuery) {
        return memberRepository.countMemberByCompanyAndRoleIn(
                memberQuery.getCompany(),
                memberQuery.getRoles()
        );
    }

    @Override
    public boolean existByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
