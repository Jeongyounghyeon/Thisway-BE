package org.thisway.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.thisway.support.common.BaseEntity;
import org.thisway.company.domain.Company;

import java.util.Set;

@Entity
@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MemberRole role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone", nullable = false))
    private PhoneNumber phone;

    @Column(nullable = false)
    private String memo;

    @Builder
    public Member(
            Company company,
            MemberRole role,
            String name,
            String email,
            String password,
            String phone,
            String memo
    ) {
        this.company = company;
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = new PhoneNumber(phone);
        this.memo = memo;
    }

    public String getPhoneValue() {
        return phone.getValue();
    }

    public String getCompanyName() {
        if (company == null) {
            return null;
        }
        return company.getName();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePhone(String phone) {
        this.phone = new PhoneNumber(phone);
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public boolean canAccess(Member targetMember) {
        return this.getAccessibleRoles().contains(targetMember.role);
    }

    private Set<MemberRole> getAccessibleRoles() {
        return role.getAccessibleRoles();
    }

    public boolean isSameCompanyAs(Member other) {
        if (this.company == null || other.company == null) {
            return false;
        }

        return this.company.equals(other.company);
    }
}
