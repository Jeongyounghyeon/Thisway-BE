package org.thisway.member.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.thisway.member.application.MemberDto;
import org.thisway.member.application.MemberInfo;
import org.thisway.support.common.PageInfo;

import java.util.List;

@Component
public class CompanyChefMemberApiContractMapper extends MemberApiContractMapper {

    public MemberDto.RegisterMemberRequest from(CompanyChefMemberApiContract.MemberRegisterRequest dto, long companyId) {
        return MemberDto.RegisterMemberRequest.builder()
                .companyId(companyId)
                .role(toMemberRole(dto.role()))
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .phone(dto.phone())
                .memo(dto.memo())
                .build();
    }

    public MemberDto.UpdateMemberRequest from(long id, CompanyChefMemberApiContract.MemberUpdateRequest dto) {
        return MemberDto.UpdateMemberRequest.builder()
                .id(id)
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .memo(dto.memo())
                .build();
    }

    public MemberDto.SearchMemberRequest from(CompanyChefMemberApiContract.MemberSearchRequest dto, long companyId) {
        return MemberDto.SearchMemberRequest.builder()
                .name(dto.memberName())
                .companyId(companyId)
                .build();
    }

    public CompanyChefMemberApiContract.MemberDetailResponse toMemberDetailResponse(MemberInfo.Member info) {
        return CompanyChefMemberApiContract.MemberDetailResponse.builder()
                .id(info.getId())
                .role(info.getRole())
                .name(info.getName())
                .email(info.getEmail())
                .phone(info.getPhone())
                .memo(info.getMemo())
                .build();
    }

    public CompanyChefMemberApiContract.MembersResponse toMembersResponse(Page<MemberInfo.Member> info) {
        List<CompanyChefMemberApiContract.MemberDetailResponse> members = info.get()
                .map(this::toMemberDetailResponse)
                .toList();
        PageInfo pageInfo = PageInfo.from(info);

        return CompanyChefMemberApiContract.MembersResponse.builder()
                .members(members)
                .pageInfo(pageInfo)
                .build();
    }

    public CompanyChefMemberApiContract.MemberSummaryResponse toMemberSummaryResponse(MemberInfo.MemberSummary info) {
        return CompanyChefMemberApiContract.MemberSummaryResponse.builder()
                .memberCount(info.getMemberCount())
                .companyChefCount(info.getCompanyChefCount())
                .companyAdminCount(info.getCompanyAdminCount())
                .build();
    }
}
