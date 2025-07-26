package org.thisway.member.domain;

public interface MemberStore {

    void store(Member member);

    void delete(Member member);
}
