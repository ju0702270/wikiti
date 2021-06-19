package com.rochez.wikiTI;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.repository.GradeRepository;
import com.rochez.wikiTI.repository.UtilisateurRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;


@SpringBootTest
public class UtilisateursTests {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private GradeRepository gradeRepository;



    @Test
    public void testRoleUser(){
        Utilisateur user = utilisateurRepository.findByMatricule("0702270");
        System.out.println(user.getRole().getLibelle());
    }

    @Test
    public void testfinOrderByNatoAscGrade(){
        for (Grade gd:gradeRepository.findByOrderByNatoAsc()) {
            System.out.println(gd.getAdministratif());
        }

    }

    @Test
    public void testFindByAllAttribute(){
        for (Utilisateur u : utilisateurRepository.findByAllAttribute("ADJ")){
            System.out.println(u.getMail() +" " + u.getMatricule());
        }
    }

    @Test
    public void testgetSearchUtilisateurParPage(){
        for (Utilisateur u : utilisateurRepository.findByAllAttribute("070", PageRequest.of(0,2))){
            System.out.println(u.getMail() +" " + u.getMatricule());
        }
    }
}
