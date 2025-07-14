package org.thisway.auth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thisway.company.domain.Company;
import org.thisway.member.domain.Member;
import org.thisway.member.domain.MemberReader;
import org.thisway.member.domain.MemberRole;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private MemberReader memberReader;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenGenerator tokenGenerator;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("로그인 성공 시 토큰 반환")
    void 로그인_성공() {
        // given
        String email = "test@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        Company company = Company.builder()
                .name("name")
                .crn("crn")
                .contact("contact")
                .addrRoad("road")
                .addrDetail("detail")
                .memo("memo")
                .gpsCycle(30)
                .build();
        Member member = Member.builder()
                .company(company)
                .role(MemberRole.MEMBER)
                .name("username")
                .email(email)
                .password(encodedPassword)
                .phone("01000000000")
                .memo("memo")
                .build();

        AuthToken expectedToken = new AuthToken("access", "refresh");

        when(memberReader.findByEmailAndActiveTrue(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(tokenGenerator.generator(member)).thenReturn(expectedToken);

        // when
        AuthToken actualResponse = authService.login(email, rawPassword);

        // then
        assertSame(expectedToken, actualResponse);
        verify(memberReader).findByEmailAndActiveTrue(email);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(tokenGenerator).generator(member);
    }

    @Test
    @DisplayName("회원이 존재하지 않을 때 예외 발생")
    void 로그인_회원_없음_예외() {
        // given
        String email = "test@example.com";
        String password = "password";

        when(memberReader.findByEmailAndActiveTrue(email)).thenReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> authService.login(email, password));
        assertEquals(ErrorCode.AUTH_MEMBER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void 로그인_비밀번호_불일치_예외() {
        // given
        String email = "test@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        Company company = Company.builder()
                .name("name")
                .crn("crn")
                .contact("contact")
                .addrRoad("road")
                .addrDetail("detail")
                .memo("memo")
                .gpsCycle(30)
                .build();
        Member member = Member.builder()
                .company(company)
                .role(MemberRole.MEMBER)
                .name("username")
                .email(email)
                .password(encodedPassword)
                .phone("01000000000")
                .memo("memo")
                .build();

        when(memberReader.findByEmailAndActiveTrue(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // when & then
        CustomException exception = assertThrows(CustomException.class,
                () -> authService.login(email, rawPassword));
        assertEquals(ErrorCode.AUTH_PASSWORD_NOT_MATCH, exception.getErrorCode());
    }
}
