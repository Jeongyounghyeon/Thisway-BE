package org.thisway.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thisway.company.domain.Company;
import org.thisway.company.domain.CompanyReader;
import org.thisway.member.domain.MemberCommand;
import org.thisway.member.domain.MemberQuery;

@Component
@RequiredArgsConstructor
public class MemberDtoMapper {

    private final CompanyReader companyReader;

    public MemberCommand.RegisterMember from(MemberDto.RegisterMemberRequest request) {
        Company company = companyReader.getById(request.companyId());

        return MemberCommand.RegisterMember.builder()
                .company(company)
                .role(request.role())
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .phone(request.phone())
                .memo(request.memo())
                .build();
    }

    public MemberCommand.UpdateMember from(MemberDto.UpdateMemberRequest request) {
        return MemberCommand.UpdateMember.builder()
                .id(request.id())
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .memo(request.memo())
                .build();
    }

    public MemberQuery.SearchMember from(MemberDto.SearchMemberRequest request) {
        Company company = companyReader.getById(request.companyId());

        return MemberQuery.SearchMember.builder()
                .name(request.name())
                .company(company)
                .build();
    }
}
