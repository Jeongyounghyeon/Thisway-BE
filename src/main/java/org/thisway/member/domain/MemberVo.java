package org.thisway.member.domain;

public class MemberVo {

    public record CompanyMemberCount(
            long companyChefCount,
            long companyAdminCount,
            long memberCount
    ) {
    }
}
