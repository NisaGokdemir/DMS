package org.gokdemir.dms.repository;

import jakarta.transaction.Transactional;
import org.gokdemir.dms.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    boolean existsByName(String name);

    @Query("SELECT c FROM Company c WHERE c.isActive = true")
    List<Company> findAllActiveCompanies();

    @Query("SELECT c FROM Company c WHERE c.isActive = false")
    List<Company> findAllInactiveCompanies();

    @Modifying
    @Transactional
    @Query("UPDATE Company c SET c.isActive = false WHERE c.id = :id")
    void deactivateCompany(@Param("id") Long id);
}
