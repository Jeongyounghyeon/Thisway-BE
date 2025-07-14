package org.thisway.member.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.thisway.support.common.PageInfo;

import java.util.List;

public class AdminMemberApiContract {

    public record MemberRegisterRequest(
            @NotNull
            Long companyId,

            @NotNull
            String role,

            @NotBlank
            String name,

            @NotBlank
            String email,

            @NotBlank
            String password,

            @NotBlank
            String phone,

            @NotNull
            String memo
    ) {
    }

    public record MemberUpdateRequest(
            @NotBlank
            String name,

            @NotBlank
            String email,

            @NotBlank
            String phone,

            @NotNull
            String memo
    ) {
    }

    @Builder
    public record MemberDetailResponse(
            long id,
            String companyName,
            String role,
            String name,
            String email,
            String phone,
            String memo
    ) {
    }

    @Builder
    public record MembersResponse(
            List<MemberDetailResponse> members,
            PageInfo pageInfo
    ) {
    }
}
