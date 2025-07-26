package org.thisway.member.application;

import lombok.Builder;
import lombok.Value;
import org.thisway.member.domain.MemberRole;

public class MemberInfo {

    @Value
    @Builder
    public static class Member {
        long id;
        String companyName;
        MemberRole role;
        String name;
        String email;
        String phone;
        String memo;
    }

    @Value
    @Builder
    public static class MemberSummary {
        long companyChefCount;
        long companyAdminCount;
        long memberCount;
    }
}
