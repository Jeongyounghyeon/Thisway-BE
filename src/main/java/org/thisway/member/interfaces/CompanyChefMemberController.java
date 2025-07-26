package org.thisway.member.interfaces;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/company-chef/members")
@RequiredArgsConstructor
public class CompanyChefMemberController {

    private final MemberFacade memberFacade;
    private final CompanyChefMemberApiContractMapper companyChefMemberApiContractMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyChefMemberApiContract.MemberDetailResponse> getMemberDetail(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long id
    ) {
        MemberInfo.Member member = memberFacade.retrieveMember(id, memberDetails.getUsername());
        CompanyChefMemberApiContract.MemberDetailResponse response = companyChefMemberApiContractMapper.toMemberDetailResponse(member);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CompanyChefMemberApiContract.MembersResponse> getMembers(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @ModelAttribute CompanyChefMemberApiContract.MemberSearchRequest search,
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        MemberDto.SearchMemberRequest searchQuery = companyChefMemberApiContractMapper.from(search, memberDetails.getCompanyId());
        Page<MemberInfo.Member> members = memberFacade.retrieveMembers(searchQuery, memberDetails.getUsername(), pageable);
        CompanyChefMemberApiContract.MembersResponse response = companyChefMemberApiContractMapper.toMembersResponse(members);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<Void> registerMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody @Validated CompanyChefMemberApiContract.MemberRegisterRequest request
    ) {
        MemberDto.RegisterMemberRequest member = companyChefMemberApiContractMapper.from(request, memberDetails.getCompanyId());
        memberFacade.registerMember(member, memberDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(
            @PathVariable long id,
            @RequestBody @Validated CompanyChefMemberApiContract.MemberUpdateRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        MemberDto.UpdateMemberRequest member = companyChefMemberApiContractMapper.from(id, request);
        memberFacade.updateMember(member, memberDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long id
    ) {
        memberFacade.deleteMember(id, memberDetails.getUsername());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/summary")
    public ResponseEntity<CompanyChefMemberApiContract.MemberSummaryResponse> summary(
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {

        MemberInfo.MemberSummary summary = memberFacade.summary(memberDetails.getCompanyId(), memberDetails.getUsername());
        CompanyChefMemberApiContract.MemberSummaryResponse response = companyChefMemberApiContractMapper.toMemberSummaryResponse(summary);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
