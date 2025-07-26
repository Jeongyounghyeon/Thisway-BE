package org.thisway.member.application;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.thisway.member.domain.Member;
import org.thisway.member.domain.MemberVo;

@Component
public class MemberInfoMapper {

    public MemberInfo.Member toMemberInfo(Member member) {
        return MemberInfo.Member.builder()
                .id(member.getId())
                .companyName(member.getCompanyName())
                .role(member.getRole())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhoneValue())
                .memo(member.getMemo())
                .build();
    }

    public Page<MemberInfo.Member> toMemberInfos(Page<Member> members) {
        return members.map(this::toMemberInfo);
    }

    public MemberInfo.MemberSummary toMemberSummary(MemberVo.CompanyMemberCount companyMemberCount) {
        return MemberInfo.MemberSummary.builder()
                .companyChefCount(companyMemberCount.companyChefCount())
                .companyAdminCount(companyMemberCount.companyAdminCount())
                .memberCount(companyMemberCount.memberCount())
                .build();
    }
}
