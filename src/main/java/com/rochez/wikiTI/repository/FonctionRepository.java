package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Fonction;
import com.rochez.wikiTI.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FonctionRepository extends JpaRepository<Fonction, Long> {
}
