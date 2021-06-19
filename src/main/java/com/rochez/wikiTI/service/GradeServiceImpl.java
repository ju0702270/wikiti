package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Implémentation du service
 */
@Service
public class GradeServiceImpl implements GradeService{
    @Autowired
    GradeRepository gradeRepository;

    @Override
    public Grade getGrade(Long id) {
        return gradeRepository.findById(id).get();
    }

    @Override
    public List<Grade> getAllGrade() {
        return gradeRepository.findAll();
    }
}
