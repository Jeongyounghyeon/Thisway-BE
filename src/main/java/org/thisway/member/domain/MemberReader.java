package org.thisway.member.domain;

import java.util.Optional;

public interface MemberReader {

    Optional<Member> findByEmailAndActiveTrue(String email);
}
