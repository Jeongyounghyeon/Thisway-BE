package org.thisway.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thisway.company.domain.Company;
import org.thisway.company.domain.CompanyService;
import org.thisway.member.domain.*;
import org.thisway.member.domain.MemberCommand;
import org.thisway.member.domain.MemberQuery;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFacade {

    private final MemberService memberService;
    private final CompanyService companyService;
    private final MemberInfoMapper memberInfoMapper;
    private final MemberDtoMapper memberDtoMapper;

    @Transactional
    public void registerMember(MemberDto.RegisterMemberRequest request, String requestedByEmail) {
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        MemberCommand.RegisterMember command = memberDtoMapper.from(request);
        memberService.registerMember(command, requestedMember);
    }

    public MemberInfo.Member retrieveMember(long id, String requestedByEmail) {
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        Member retrieveMember = memberService.getMember(id, requestedMember);
        return memberInfoMapper.toMemberInfo(retrieveMember);
    }

    public Page<MemberInfo.Member> retrieveMembers(String requestedByEmail, Pageable pageable) {
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        Page<Member> retrieveMembers = memberService.getMembers(requestedMember, pageable);
        return memberInfoMapper.toMemberInfos(retrieveMembers);
    }

    public Page<MemberInfo.Member> retrieveMembers(MemberDto.SearchMemberRequest request, String requestedByEmail, Pageable pageable) {
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        MemberQuery.SearchMember searchQuery = memberDtoMapper.from(request);
        Page<Member> retrieveMembers = memberService.getMembers(searchQuery, requestedMember, pageable);
        return memberInfoMapper.toMemberInfos(retrieveMembers);
    }

    @Transactional
    public void updateMember(MemberDto.UpdateMemberRequest request, String requestedByEmail) {
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        MemberCommand.UpdateMember command = memberDtoMapper.from(request);
        memberService.updateMember(command, requestedMember);
    }

    @Transactional
    public void deleteMember(Long id, String requestedByEmail) {
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        memberService.deleteMember(id, requestedMember);
    }

    public MemberInfo.MemberSummary summary(long companyId, String requestedByEmail) {
        Company company = companyService.getCompany(companyId);
        Member requestedMember = memberService.getMemberByEmail(requestedByEmail);
        MemberVo.CompanyMemberCount companyMemberCount = memberService.summaryCompanyMember(company, requestedMember);
        return memberInfoMapper.toMemberSummary(companyMemberCount);
    }
}
