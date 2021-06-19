package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Utilisateur;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GradeService {
    Grade getGrade(Long id);
    List<Grade> getAllGrade();
}

