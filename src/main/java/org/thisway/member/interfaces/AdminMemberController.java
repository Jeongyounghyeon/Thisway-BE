package org.thisway.member.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thisway.member.application.MemberFacade;
import org.thisway.member.application.MemberDto;
import org.thisway.member.application.MemberInfo;
import org.thisway.support.security.dto.request.MemberDetails;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberFacade memberFacade;
    private final AdminMemberApiContractMapper adminMemberApiContractMapper;

    @PostMapping
    public ResponseEntity<Void> registerMember(
            @RequestBody @Validated AdminMemberApiContract.MemberRegisterRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        MemberDto.RegisterMemberRequest registerRequest = adminMemberApiContractMapper.from(request);
        memberFacade.registerMember(registerRequest, memberDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminMemberApiContract.MemberDetailResponse> getMemberDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        MemberInfo.Member member = memberFacade.retrieveMember(id, memberDetails.getUsername());
        AdminMemberApiContract.MemberDetailResponse response = adminMemberApiContractMapper.toMemberDetailResponse(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<AdminMemberApiContract.MembersResponse> getMembers(
            @PageableDefault Pageable pageable,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        Page<MemberInfo.Member> members = memberFacade.retrieveMembers(memberDetails.getUsername(), pageable);
        AdminMemberApiContract.MembersResponse response = adminMemberApiContractMapper.toMembersResponse(members);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(
            @PathVariable long id,
            @RequestBody @Validated AdminMemberApiContract.MemberUpdateRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        MemberDto.UpdateMemberRequest updateCommand = adminMemberApiContractMapper.from(request, id);
        memberFacade.updateMember(updateCommand, memberDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        memberFacade.deleteMember(id, memberDetails.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
