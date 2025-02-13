package org.gokdemir.dms.mapper;

import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.entity.Document;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface DocumentMapper {
    // 📌 DtoDocumentIU sınıfından Document sınıfına dönüşüm yapacak olan map metodu
    Document toEntity(DtoDocumentIU dtoDocumentIU);

    // 📌 Document sınıfından DtoDocument sınıfına dönüşüm yapacak olan map metodu
    DtoDocument toDto(Document document);

    // 📌 Document sınıfından DtoDocument sınıfına dönüşüm yapacak olan map metodu
    List<DtoDocument> toDtoList(List<Document> documentList);
}
