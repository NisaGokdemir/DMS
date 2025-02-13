package org.gokdemir.dms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gokdemir.dms.dto.request.DtoDocumentIU;
import org.gokdemir.dms.dto.response.DtoDocument;
import org.gokdemir.dms.entity.Company;
import org.gokdemir.dms.entity.Document;
import org.gokdemir.dms.mapper.DocumentMapper;
import org.gokdemir.dms.repository.CompanyRepository;
import org.gokdemir.dms.repository.DocumentRepository;
import org.gokdemir.dms.service.IDocumentService;
import org.gokdemir.dms.util.DocumentUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements IDocumentService {

    private final DocumentRepository documentRepository;

    private final CompanyRepository companyRepository;

    private final DocumentMapper documentMapper;



    private Company getCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    private Document getDocument(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    private void validateNotArchived(Document document) {
        if (!document.isActive()) {
            throw new RuntimeException("Document is already archived");
        }
    }

    private void validateNotActive(Document document) {
        if (document.isActive()) {
            throw new RuntimeException("Document is already active");
        }
    }



    @Transactional
    public DtoDocument uploadDocument(MultipartFile file, DtoDocumentIU dtoDocumentIU) throws IOException {
        Company company = getCompany(dtoDocumentIU.getCompanyId());

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("Invalid file name: Extension is missing");
        }

        String uniqueFilename = DocumentUtils.generateUniqueFilename(originalFilename);
        Path filePath = Path.of(company.getFolderPath(), uniqueFilename);
        DocumentUtils.saveFile(file, filePath);

        // Yeni oluşturulan Document nesnesine DTO'dan gelen bilgileri de set ediyoruz.
        Document document = documentMapper.toEntity(dtoDocumentIU);
        document.setName(uniqueFilename);
        document.setCompany(company);
        document.setActive(true);

        Document savedDocument = documentRepository.save(document);
        return documentMapper.toDto(savedDocument);
    }

    @Transactional
    public String archiveDocument(Long documentId) {
        Document document = getDocument(documentId);

        validateNotActive(document);

        Company company = document.getCompany();
        Path oldPath = Path.of(company.getFolderPath(), document.getName());
        Path newPath = Path.of(company.getFolderPath(), "archived_" + document.getName());

        try {
            DocumentUtils.moveFile(oldPath, newPath);
        } catch (IOException e) {
            throw new RuntimeException("Error while archiving document", e);
        }

        document.setName("archived_" + document.getName());
        document.setActive(false);
        documentRepository.save(document);

        return "Document archived successfully";
    }

    @Transactional
    public String restoreDocument(Long documentId) {
        Document document = getDocument(documentId);

        validateNotArchived(document);

        Company company = document.getCompany();
        String originalName = document.getName().replaceFirst("^archived_", "");
        Path oldPath = Path.of(company.getFolderPath(), document.getName());
        Path newPath = Path.of(company.getFolderPath(), originalName);

        try {
            DocumentUtils.moveFile(oldPath, newPath);
        } catch (IOException e) {
            throw new RuntimeException("Error while restoring document", e);
        }

        document.setName(originalName);
        document.setActive(true);
        documentRepository.save(document);

        return "Document restored successfully";
    }

    @Transactional
    public List<DtoDocument> getActiveDocumentsByCompany(Long companyId) {
        List<Document> documents = documentRepository.findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(companyId);
        return documentMapper.toDtoList(documents);
    }

    @Transactional
    public List<DtoDocument> getArchivedDocumentsByCompany(Long companyId) {
        List<Document> documents = documentRepository.findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(companyId);
        return documentMapper.toDtoList(documents);
    }

    //    @Transactional
//    public byte[] viewDocument(Long documentId) throws IOException {
//        Document document = getDocument(documentId);
//
//        Path filePath = Path.of(document.getCompany().getFolderPath(), document.getName());
//        if (!filePath.toFile().exists()) {
//            throw new RuntimeException("File not found");
//        }
//
//        return DocumentUtils.readFile(filePath);
//    }
}