package org.gokdemir.dms.service;

import org.gokdemir.dms.dto.request.DtoCompanyIU;
import org.gokdemir.dms.dto.response.DtoCompany;
import org.gokdemir.dms.entity.Company;

import java.util.List;

public interface ICompanyService {

    //save - getactive - getinactive - deactivate - update

    public DtoCompany saveCompany(DtoCompanyIU dtoCompanyIU);

    public DtoCompany updateCompany(Long id, DtoCompanyIU dtoCompanyIU);

    public List<DtoCompany> getActiveCompanies();

    public List<DtoCompany> getInactiveCompanies();

    public void deactivateCompany(Long id);
}
