package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByOrderByNatoAsc();
}
