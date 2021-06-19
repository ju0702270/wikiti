package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Categorie;
import com.rochez.wikiTI.model.DegreSecurite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
