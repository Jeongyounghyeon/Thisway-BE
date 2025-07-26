package org.thisway.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.thisway.company.domain.Company;

public interface MemberService {

    Member getMemberByEmail(String email);

    void registerMember(MemberCommand.RegisterMember command, Member actor);

    Member getMember(long id, Member actor);

    Page<Member> getMembers(Member actor, Pageable pageable);

    Page<Member> getMembers(MemberQuery.SearchMember searchQuery, Member actor, Pageable pageable);

    void updateMember(MemberCommand.UpdateMember command, Member actor);

    void deleteMember(long id, Member actor);

    MemberVo.CompanyMemberCount summaryCompanyMember(Company company, Member actor);
}
