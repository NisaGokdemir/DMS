package org.gokdemir.dms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.controller.IRestDocumentController;
import org.gokdemir.dms.controller.RestBaseController;
import org.gokdemir.dms.controller.RootEntity;
import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.service.IDocumentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/document")
public class RestDocumentControllerImpl extends RestBaseController implements IRestDocumentController {

    private final IDocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public RootEntity<DtoDocument> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestPart("dto") DtoDocumentIU dtoDocumentIU) throws IOException {
        return ok(documentService.uploadDocument(file, dtoDocumentIU));
    }

    @PostMapping("/archive/{documentId}")
    @Override
    public RootEntity<String> archiveDocument(@PathVariable Long documentId) {
        return ok(documentService.archiveDocument(documentId));
    }

    @PostMapping("/restore/{documentId}")
    @Override
    public RootEntity<String> restoreDocument(@PathVariable Long documentId) {
        return ok(documentService.restoreDocument(documentId));
    }

    @GetMapping("/active/{companyId}")
    @Override
    public RootEntity<List<DtoDocument>> getActiveDocumentsByCompany(@PathVariable Long companyId) {
        return ok(documentService.getActiveDocumentsByCompany(companyId));
    }

    @GetMapping("/archived/{companyId}")
    @Override
    public RootEntity<List<DtoDocument>> getArchivedDocumentsByCompany(@PathVariable Long companyId) {
        return ok(documentService.getArchivedDocumentsByCompany(companyId));
    }

    //    @GetMapping("/view/{documentId}")
//    @Override
//    public void viewDocument(@PathVariable Long documentId, HttpServletResponse response) throws IOException {
//        byte[] fileData = documentService.viewDocument(documentId);
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.getOutputStream().write(fileData);
//        response.getOutputStream().flush();
//    }
}
