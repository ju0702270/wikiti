package com.rochez.wikiTI;

import com.rochez.wikiTI.model.*;
import com.rochez.wikiTI.repository.DocumentationRepository;
import com.rochez.wikiTI.service.*;
import com.rochez.wikiTI.utility.StringUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class DocumentationTests {
    @Autowired
    DocumentationService documentationService;
    @Autowired
    DocumentationRepository documentationRepository;
    @Autowired
    CategorieService categorieService;
    @Autowired
    LangueService langueService;
    @Autowired
    TypeService typeService;
    @Autowired
    DegreSecuriteService degreSecuriteService;
    @Autowired
    SectionContenuService sectionContenuService;
    @Autowired
    UtilisateurService utilisateurService;


    private static Documentation documentation;
    private static SectionContenu sectionContenu;

    @BeforeEach
    public void createNewDoc(){
        documentation = new Documentation();
        sectionContenu= new SectionContenu();
    }



    /**
     * Méthode de test pour verifier si documentationRepository.findByAllAttribute ne retrourne que des documentations
     * dont les degreSecurités ne sont pas plus haut que le degreSecurite demandé
     */
    @Test
    public void searchDocWhereDegreSecurite(){
        DegreSecurite degreSecurite = new DegreSecurite(2L,"Restricted","first step of restrication");

        HashSet<Documentation> documentations = documentationRepository.findByAllAttribute(degreSecurite,"");
        for (Documentation doc: documentations ) {
            System.out.println(doc.getDegreSecurite().getId());
            assertTrue(degreSecurite.getId() >= doc.getDegreSecurite().getId());
        }
    }

    /**
     * Méthode de test pour verifier si documentationRepository.findByAllAttribute fait des recherche convenable en
     * lui passant une chaine de caractère
     */
    @Test
    public void searchDocWhereSearchValue() throws ParseException {
        DegreSecurite degreSecurite = new DegreSecurite(2L,"Restricted","first step of restrication");

        //verification si le mot cherché est dans mon scope
        HashSet<Documentation>  documentations =  documentationRepository.findByAllAttribute(degreSecurite,"SCOPE");
        for (Documentation doc: documentations ) {
            assertTrue(Pattern.compile(Pattern.quote("scope"), Pattern.CASE_INSENSITIVE).matcher(doc.getScope()).find());
        }

        //verification si le mot cherché se trouve dans un sectionContenu du document
        documentations = documentationRepository.findByAllAttribute(degreSecurite,"voiture");
        int count = 0;
        for (Documentation doc: documentations ) {
            List<SectionContenu> sectionContenus = sectionContenuService.getAllSectionContenuByDocumentation(doc);
            for ( SectionContenu sc : sectionContenus) {
                if (sc.getHtml().contains("voiture")){//on va compter qu'il y a minimum 1 sectionContenu de la documentation contenant le mot
                    count++;
                }
            }
            assertTrue(count>0);
        }
    }

    /**
     * Méthode de vérification si la recherche par type est correcte
     */
    @Test
    public void searchByType(){
        Type type = typeService.getType(1L);
        HashSet<Documentation> listDoc = documentationRepository.findByType(
                utilisateurService.getUtilisateur(1L).getDegreSecurite(),type);
        for (Documentation doc:listDoc) {
            assertEquals(doc.getType(), type);
        }
    }

    /**
     * Méthode de vérification si la recherche par catégorie est correcte
     */
    @Test
    public void searchByCategorie(){
        Categorie categorie = categorieService.getCategorie(1L);
        HashSet<Documentation> listDoc = documentationRepository.findByCategorie(
                utilisateurService.getUtilisateur(1L).getDegreSecurite(),categorie);
        for (Documentation doc:listDoc) {
            doc.getCategories().contains(categorie);
        }
    }

    @Test
    public void searchByTitre(){
        HashSet<Documentation> listDoc = documentationRepository.findByTitre(
                utilisateurService.getUtilisateur(1L).getDegreSecurite(),"Titre");
        for (Documentation doc:listDoc) {
            assertTrue(Pattern.compile(Pattern.quote("titre"), Pattern.CASE_INSENSITIVE).matcher(doc.getTitre()).find());
        }
    }

    @Test
    public void searchByUtilisateur(){
        HashSet<Documentation> listDoc = documentationRepository.findByUtilisateurCreateur(
                utilisateurService.getUtilisateur(1L).getDegreSecurite(),"Rochez");
        for (Documentation doc:listDoc) {
            assertTrue(Pattern.compile(Pattern.quote("Rochez"), Pattern.CASE_INSENSITIVE).matcher(doc.getUtilisateur_createur().getNom()).find());
        }
    }


}
