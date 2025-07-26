package org.thisway.member.domain;

import lombok.Builder;
import lombok.Value;
import org.thisway.company.domain.Company;

public class MemberCommand {

    @Value
    @Builder
    public static class RegisterMember {
        Company company;
        MemberRole role;
        String name;
        String email;
        String password;
        String phone;
        String memo;
    }

    @Value
    @Builder
    public static class UpdateMember {
        long id;
        String name;
        String email;
        String phone;
        String memo;
    }
}
