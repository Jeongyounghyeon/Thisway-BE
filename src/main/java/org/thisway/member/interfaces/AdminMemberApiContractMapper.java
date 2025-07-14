package org.thisway.member.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.thisway.member.application.MemberDto;
import org.thisway.member.application.MemberInfo;
import org.thisway.support.common.PageInfo;

import java.util.List;

@Component
public class AdminMemberApiContractMapper extends MemberApiContractMapper {

    public MemberDto.RegisterMemberRequest from(AdminMemberApiContract.MemberRegisterRequest dto) {
        return MemberDto.RegisterMemberRequest.builder()
                .companyId(dto.companyId())
                .role(toMemberRole(dto.role()))
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .phone(dto.phone())
                .memo(dto.memo())
                .build();
    }

    public MemberDto.UpdateMemberRequest from(AdminMemberApiContract.MemberUpdateRequest dto, long id) {
        return MemberDto.UpdateMemberRequest.builder()
                .id(id)
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .memo(dto.memo())
                .build();
    }

    public AdminMemberApiContract.MemberDetailResponse toMemberDetailResponse(MemberInfo.Member info) {
        return AdminMemberApiContract.MemberDetailResponse.builder()
                .id(info.getId())
                .companyName(info.getCompanyName())
                .role(info.getRole().name())
                .name(info.getName())
                .email(info.getEmail())
                .phone(info.getPhone())
                .memo(info.getMemo())
                .build();
    }

    public AdminMemberApiContract.MembersResponse toMembersResponse(Page<MemberInfo.Member> info) {
        List<AdminMemberApiContract.MemberDetailResponse> members = info.get()
                .map(this::toMemberDetailResponse)
                .toList();
        PageInfo pageInfo = PageInfo.from(info);

        return AdminMemberApiContract.MembersResponse.builder()
                .members(members)
                .pageInfo(pageInfo)
                .build();
    }
}
