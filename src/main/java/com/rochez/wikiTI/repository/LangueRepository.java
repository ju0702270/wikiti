package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Langue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LangueRepository extends JpaRepository<Langue, Long> {
}
