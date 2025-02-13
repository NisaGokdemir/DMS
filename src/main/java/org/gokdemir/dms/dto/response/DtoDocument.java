package org.gokdemir.dms.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.gokdemir.dms.enums.DocumentCategory;
import org.gokdemir.dms.enums.DocumentFormat;

@Getter
@Setter
public class DtoDocument {

    private String name;

    private DocumentFormat type;

    private DocumentCategory category;

    private String description;

    private DtoCompany company;
}
