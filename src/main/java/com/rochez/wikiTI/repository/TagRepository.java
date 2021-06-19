package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByLibelle(String libelle);
}
