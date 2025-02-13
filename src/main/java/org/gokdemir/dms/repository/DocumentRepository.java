package org.gokdemir.dms.repository;

import org.gokdemir.dms.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCompanyIdAndIsActiveTrueOrderByCreatedAtDesc(Long companyId);
    List<Document> findByCompanyIdAndIsActiveFalseOrderByCreatedAtDesc(Long companyId);

}
