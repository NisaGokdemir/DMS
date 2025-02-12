package org.gokdemir.dms.controller.impl;

import org.gokdemir.dms.controller.IRestCompanyController;
import org.gokdemir.dms.controller.RestBaseController;
import org.gokdemir.dms.controller.RootEntity;
import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class RestCompanyControllerImpl extends RestBaseController implements IRestCompanyController {
    @Override
    public RootEntity<DtoCompany> saveCompany(DtoCompanyIU dtoCompanyIU) {
        return null;
    }

    @Override
    public RootEntity<DtoCompany> updateCompany(Long id, DtoCompanyIU dtoCompanyIU) {
        return null;
    }

    @Override
    public RootEntity<List<DtoCompany>> getActiveCompanies() {
        return null;
    }

    @Override
    public RootEntity<List<DtoCompany>> getInactiveCompanies() {
        return null;
    }

    @Override
    public RootEntity<String> deactivateCompany(Long id) {
        return null;
    }

    @Override
    public RootEntity<String> activateCompany(Long id) {
        return null;
    }
}
