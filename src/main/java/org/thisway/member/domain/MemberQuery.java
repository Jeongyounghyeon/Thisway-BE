package org.thisway.member.domain;

import lombok.Builder;
import lombok.Value;
import org.thisway.company.domain.Company;

import java.util.Collection;

public class MemberQuery {

    @Value
    @Builder(toBuilder = true)
    public static class SearchMember {
        Company company;
        Collection<MemberRole> roles;
        String name;
    }
}
