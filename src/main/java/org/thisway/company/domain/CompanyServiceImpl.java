package org.thisway.company.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyReader companyReader;

    @Override
    public Company getCompany(long id) {
        return companyReader.getById(id);
    }
}
