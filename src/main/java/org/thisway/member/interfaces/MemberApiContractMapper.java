package org.thisway.member.interfaces;

import org.springframework.stereotype.Component;
import org.thisway.member.domain.MemberRole;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;

@Component
public class MemberApiContractMapper {

    protected MemberRole toMemberRole(String role) {
        try {
            return MemberRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.MEMBER_INVALID_MEMBER_ROLE);
        }
    }
}
