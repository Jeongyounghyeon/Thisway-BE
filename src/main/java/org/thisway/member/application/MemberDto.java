package org.thisway.member.application;

import lombok.Builder;
import org.thisway.member.domain.MemberRole;

public class MemberDto {

    @Builder
    public record RegisterMemberRequest(
            long companyId,
            MemberRole role,
            String name,
            String email,
            String password,
            String phone,
            String memo
    ) {
    }

    @Builder
    public record UpdateMemberRequest(
            long id,
            String name,
            String email,
            String phone,
            String memo
    ) {
    }

    @Builder
    public record SearchMemberRequest(
            long companyId,
            String name
    ) {
    }
}
