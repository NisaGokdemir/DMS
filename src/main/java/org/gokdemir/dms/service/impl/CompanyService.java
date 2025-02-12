package org.gokdemir.dms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.exception.BaseException;
import org.gokdemir.dms.exception.ErrorMessage;
import org.gokdemir.dms.exception.MessageType;
import org.gokdemir.dms.mapper.CompanyMapper;
import org.gokdemir.dms.util.CompanyFolderUtils;
import org.springframework.beans.factory.annotation.Value;
import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.repository.CompanyRepository;
import org.gokdemir.dms.service.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Value("${company.base.folder}")
    private String baseFolderPath;

    private Company createCompany(Company company) {
        validateCompanyName(company.getName());
        String companyFolderPath = CompanyFolderUtils.createCompanyFolder(baseFolderPath, company.getName());
        company.setFolderPath(companyFolderPath);
        return company;
    }

    private void validateCompanyName(String name) {
        if (companyRepository.existsByName(name)) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_EXISTS, name));
        }
    }

    private Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
    }

    @Transactional
    public DtoCompany saveCompany(DtoCompanyIU dtoCompanyIU) {
        Company company = createCompany(companyMapper.toEntity(dtoCompanyIU));
        companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Transactional
    public DtoCompany updateCompany(Long id, DtoCompanyIU dtoCompanyIU) {
        Company company = getCompanyById(id);

        if (!company.getName().equals(dtoCompanyIU.getName())) {
            validateCompanyName(dtoCompanyIU.getName());
        }

        company.setName(dtoCompanyIU.getName());
        companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Transactional
    public List<DtoCompany> getActiveCompanies() {
        List<Company> companies = companyRepository.findAllActiveCompanies();
        return companyMapper.toDtoList(companies);
    }

    @Transactional
    public List<DtoCompany> getInactiveCompanies() {
        List<Company> companies = companyRepository.findAllInactiveCompanies();
        return companyMapper.toDtoList(companies);
    }

    @Transactional
    public String deactivateCompany(Long id) {
        Company company = getCompanyById(id);
        checkIfCompanyIsInactive(company);

        String archivedFolderPath = CompanyFolderUtils.renameCompanyFolder(
                company.getFolderPath(),
                CompanyFolderUtils.getArchivedFolderPath(company.getFolderPath()),
                company.getName()
        );

        company.setFolderPath(archivedFolderPath);
        company.setActive(false);
        companyRepository.save(company);

        return "Company deactivated successfully";
    }

    @Transactional
    public String activateCompany(Long id) {
        Company company = getCompanyById(id);
        checkIfCompanyIsActive(company);

        String activeFolderPath = CompanyFolderUtils.renameCompanyFolder(
                company.getFolderPath(),
                CompanyFolderUtils.removeArchivedPrefix(company.getFolderPath()),
                company.getName()
        );

        company.setFolderPath(activeFolderPath);
        company.setActive(true);
        companyRepository.save(company);
        return "Company activated successfully";
    }

    private void checkIfCompanyIsActive(Company company) {
        if (company.isActive()) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_ACTIVE, company.getName()));
        }
    }

    private void checkIfCompanyIsInactive(Company company) {
        if (!company.isActive()) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_INACTIVE, company.getName()));
        }
    }
}