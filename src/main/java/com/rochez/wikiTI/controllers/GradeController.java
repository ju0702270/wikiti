package com.rochez.wikiTI.controllers;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/grade")
public class GradeController {
    //TODO delete
    @Autowired
    GradeRepository gradeRepository;

    /*@RequestMapping(value = "listGrade", method = RequestMethod.POST, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Grade>> getJsonAllGrade() {
        try {
            List<Grade> list = gradeRepository.findAll();
            ResponseEntity<List<Grade>> response = new ResponseEntity<List<Grade>>(list, HttpStatus.OK);
            return response;
        } catch (Exception e) {
            return  new ResponseEntity<List<Grade>>(HttpStatus.BAD_REQUEST);
        }
    }*/
}
