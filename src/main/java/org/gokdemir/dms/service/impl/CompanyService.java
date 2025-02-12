package org.gokdemir.dms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.exception.BaseException;
import org.gokdemir.dms.exception.ErrorMessage;
import org.gokdemir.dms.exception.MessageType;
import org.springframework.beans.factory.annotation.Value;
import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.repository.CompanyRepository;
import org.gokdemir.dms.service.ICompanyService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;

    // 📌 Firmaların kayıt edileceği ana klasör (application.properties dosyasından alınacak)
    @Value("${company.base.folder}")
    private String baseFolderPath;


    @Transactional
    public Company createCompany(Company company) {
        validateCompanyName(company.getName());
        String companyFolderPath = createCompanyFolder(company.getName());
        company.setFolderPath(companyFolderPath);
        return company;
    }


    private void validateCompanyName(String name) {
        if (companyRepository.existsByName(name)) {
            throw new BaseException(new ErrorMessage(MessageType.COMPANY_ALREADY_EXISTS, name));
        }
    }


    private String createCompanyFolder(String companyName) {
        String companyFolderPath = baseFolderPath + File.separator + companyName;
        File companyFolder = new File(companyFolderPath);

        if (!companyFolder.exists()) {
            boolean created = companyFolder.mkdirs();
            if (!created) {
                throw new BaseException(new ErrorMessage(MessageType.FOLDER_CREATION_FAILED, companyName));
            }
        }

        return companyFolderPath;
    }



    public DtoCompany saveCompany(DtoCompanyIU dtoCompanyIU) {
        return null;
    }

    public DtoCompany updateCompany(Long id, DtoCompanyIU dtoCompanyIU) {
        return null;
    }

    public List<DtoCompany> getActiveCompanies() {
        return null;
    }

    public List<DtoCompany> getInactiveCompanies() {
        return null;
    }

    public void deactivateCompany(Long id) {
    }
}
