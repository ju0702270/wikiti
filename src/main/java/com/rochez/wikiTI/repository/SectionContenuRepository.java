package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Documentation;
import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.SectionContenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectionContenuRepository extends JpaRepository<SectionContenu, Long> {
    List<SectionContenu> findByOrderByOrdreAsc();
    @Query("select sc from SectionContenu sc where sc.documentation.id = ?1")
    List<SectionContenu> findAllByDocumentation(Long id);
}
