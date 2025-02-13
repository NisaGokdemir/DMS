package org.gokdemir.dms.controller;

import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRestDocumentController {

    public RootEntity<DtoDocument> uploadDocument(MultipartFile file, DtoDocumentIU dtoDocumentIU) throws IOException;

    public RootEntity<String> archiveDocument(Long documentId);

    public RootEntity<String> restoreDocument(Long documentId);

    public RootEntity<List<DtoDocument>> getActiveDocumentsByCompany(Long companyId);

    public RootEntity<List<DtoDocument>> getArchivedDocumentsByCompany(Long companyId);
}
