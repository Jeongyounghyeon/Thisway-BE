package org.thisway.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface MemberReader {

    Member getMember(long id);

    Member getMemberByEmail(String email);

    Page<Member> getMembers(Collection<MemberRole> accessibleRole, Pageable pageable);

    Page<Member> getMembers(MemberQuery.SearchMember memberQuery, Pageable pageable);

    long countMember(MemberQuery.SearchMember memberQuery);

    boolean existByEmail(String email);
}
