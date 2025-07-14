package org.thisway.company.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thisway.company.domain.Company;
import org.thisway.company.domain.CompanyReader;
import org.thisway.support.common.BaseEntity;
import org.thisway.support.common.CustomException;
import org.thisway.support.common.ErrorCode;

@Component
@RequiredArgsConstructor
public class CompanyReaderImpl implements CompanyReader {

    private final CompanyRepository companyRepository;

    @Override
    public Company getById(long id) {
        return companyRepository.findById(id)
                .filter(BaseEntity::isActive)
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
    }
}
