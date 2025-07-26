package org.thisway.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thisway.company.domain.Company;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final MemberCommandMapper memberCommandMapper;
    private final MemberQueryMapper memberQueryMapper;

    @Override
    public Member getMemberByEmail(String email) {
        return memberReader.getMemberByEmail(email);
    }

    @Override
    public void registerMember(MemberCommand.RegisterMember command, Member actor) {
        Member member = memberCommandMapper.from(command);
        if (!actor.canAccess(member)) {
            throw new CustomException(ErrorCode.MEMBER_ACCESS_DENIED);
        }
        assertDuplicateEmail(member.getEmail());
        memberStore.store(member);
    }

    @Override
    public Member getMember(long id, Member actor) {
        Member member = memberReader.getMember(id);
        assertAccess(actor, member);
        return member;
    }

    @Override
    public Page<Member> getMembers(Member actor, Pageable pageable) {
        Set<MemberRole> accessibleRole = actor.getRole().getAccessibleRoles();
        return memberReader.getMembers(accessibleRole, pageable);
    }

    @Override
    public Page<Member> getMembers(MemberQuery.SearchMember searchQuery, Member actor, Pageable pageable) {
        Set<MemberRole> accessibleRole = actor.getRole().getAccessibleRoles();
        MemberQuery.SearchMember searchMember = memberQueryMapper.overrideRoles(searchQuery, accessibleRole);
        return memberReader.getMembers(searchMember, pageable);
    }

    @Override
    @Transactional
    public void updateMember(MemberCommand.UpdateMember command, Member actor) {
        Member member = memberReader.getMember(command.getId());
        assertAccess(actor, member);
        assertDuplicateEmail(command.getEmail());

        member.updateName(command.getName());
        member.updateEmail(command.getEmail());
        member.updatePhone(command.getPhone());
        member.updateMemo(command.getMemo());
    }

    @Override
    @Transactional
    public void deleteMember(long id, Member actor) {
        Member member = memberReader.getMember(id);
        assertAccess(actor, member);
        memberStore.delete(member);
    }

    @Override
    public MemberVo.CompanyMemberCount summaryCompanyMember(Company company, Member actor) {
        Company actorCompany = actor.getCompany();
        if (!company.equals(actorCompany)) {
            throw new CustomException(ErrorCode.COMPANY_ACCESS_DENIED);
        }
        long companyChefCount = countByCompanyAndRole(company, MemberRole.COMPANY_CHEF);
        long companyAdminCount = countByCompanyAndRole(company, MemberRole.COMPANY_ADMIN);
        long memberCount = countByCompanyAndRole(company, MemberRole.MEMBER);

        return new MemberVo.CompanyMemberCount(
                companyChefCount,
                companyAdminCount,
                memberCount
        );
    }

    private long countByCompanyAndRole(Company company, MemberRole role) {
        MemberQuery.SearchMember query = MemberQuery.SearchMember.builder()
                .company(company)
                .roles(List.of(role))
                .build();

        return memberReader.countMember(query);
    }

    private static void assertAccess(Member actor, Member target) {
        if (!actor.canAccess(target)) {
            throw new CustomException(ErrorCode.MEMBER_ACCESS_DENIED);
        }
    }

    private void assertDuplicateEmail(String email) {
        if (memberReader.existByEmail(email)) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_EXIST_BY_EMAIL);
        }
    }
}
