package org.thisway.member.domain;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MemberQueryMapper {

    public MemberQuery.SearchMember overrideRoles(MemberQuery.SearchMember query, Collection<MemberRole> roles) {
        return query.toBuilder()
                .roles(roles)
                .build();
    }
}
