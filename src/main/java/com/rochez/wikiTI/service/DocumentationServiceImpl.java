package com.rochez.wikiTI.service;


import com.rochez.wikiTI.model.*;
import com.rochez.wikiTI.repository.DocumentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Implémentation du service
 */
@Service
public class DocumentationServiceImpl implements DocumentationService{
    @Autowired
    DocumentationRepository documentationRepository;
    @Autowired
    SectionContenuService sectionContenuService;
    /**
     * Métohde de persistance d'une documentation en base de donnée
     * Cette méthode vérifie si les Tag en rapport avec les sectionContenus de la documentation,
     * ne sont pas avec un libelle null ou s'il ne sont pas déja en base de donnée.
     * Auquel cas celui-ci ne sera pas dupliqué mais prendra le Tag déja existant en compte
     * @param documentation : l'objet Documentation à persister en base de donnée
     * @return
     */
    @Override
    public Documentation saveDocumentation(DegreSecurite degreSecurite,Documentation documentation) {
        if(degreSecurite.getId() >= documentation.getDegreSecurite().getId()){
            for (SectionContenu sectionContenu: documentation.getSectionContenus() ) {
                sectionContenu.setDocumentation(documentation);
            }
            return documentationRepository.save(documentation);
        }
        return null;
    }

    /**
     * Méthode de suppression de documentation, cette méthode supprime aussi les sectionContenu associé à la documentation
     * @param documentation
     * @return boolean
     */
    @Override
    public boolean deleteDocumentation(Documentation documentation) {
        for (SectionContenu sectionContenu: documentation.getSectionContenus()) {
            sectionContenuService.deleteSectionContenu(sectionContenu);
        }
        documentationRepository.delete(documentation);
        return documentationRepository.findById(documentation.getId()).isEmpty();
    }

    @Override
    public Documentation getDocumentation(Long id) {
        return documentationRepository.findById(id).get();
    }

    @Override
    public List<Documentation> getAllDocumentation() {
        return documentationRepository.findAll();
    }

    @Override
    public Page<Documentation> getAllDocumentationByAttribute(Utilisateur utilisateur, String searchValue, int size, int page) {
        return documentationRepository.findByAllAttribute(utilisateur.getDegreSecurite()
                ,searchValue, PageRequest.of(page,size));
    }

    @Override
    public HashSet<Documentation> getAllDocumentationByAttribute(Utilisateur utilisateur, String searchValue) {
        return documentationRepository.findByAllAttribute(utilisateur.getDegreSecurite(),searchValue);
    }

    @Override
    public HashSet<Documentation> getAllDocumentatioByType(Utilisateur utilisateur, Type type) {
        return documentationRepository.findByType(utilisateur.getDegreSecurite(),type);
    }

    @Override
    public HashSet<Documentation> getAllDocumentatioByCategorie(Utilisateur utilisateur, Categorie categorie) {
        return documentationRepository.findByCategorie(utilisateur.getDegreSecurite(),categorie);
    }

    @Override
    public HashSet<Documentation> getAllDocumentatioByTitre(Utilisateur utilisateur, String Titre) {
        return documentationRepository.findByTitre(utilisateur.getDegreSecurite(), Titre);
    }

    @Override
    public HashSet<Documentation> getAllDocumentatioByUtilisateurCreateur(Utilisateur utilisateur, String utilisateur_createur_name) {
        return documentationRepository.findByUtilisateurCreateur(utilisateur.getDegreSecurite(), utilisateur_createur_name);
    }

    @Override
    public HashSet<Documentation> getAllDocumentatioByFourAttribute(Utilisateur utilisateur, String utilisateur_createur_name, Type type, String Titre, Categorie categorie) {
        return documentationRepository.findByFourAttribute(utilisateur.getDegreSecurite(),type,categorie,Titre,utilisateur_createur_name);
    }

    @Override
    public HashSet<Documentation> getAllDocumentatioBySixAttribute(Utilisateur utilisateur, DegreSecurite degreSecurite, Langue langue, String utilisateur_createur_name, Type type, String Titre, Categorie categorie) {
        return documentationRepository.findBySixAttribute(degreSecurite,langue,utilisateur.getDegreSecurite(), type,categorie,Titre, utilisateur_createur_name);
    }

    @Override
    public HashSet<Documentation> getAllByUtilisateurCreateur(Utilisateur utilisateur) {
        return  documentationRepository.findByUtilisateurCreateur(utilisateur);
    }

    @Override
    public HashSet<Documentation> getAllByUtilisateurCourant(Utilisateur utilisateur) {
        return documentationRepository.findAllByDegreSecurite(utilisateur.getDegreSecurite());
    }

    @Override
    public Page<Documentation> getAllDocumentatioByType(Utilisateur utilisateur, Type type, int size, int page) {
        return documentationRepository.findByType(utilisateur.getDegreSecurite(),type, PageRequest.of(page,size));
    }

    @Override
    public Page<Documentation> getAllDocumentatioByCategorie(Utilisateur utilisateur, Categorie categorie, int size, int page) {
        return documentationRepository.findByCategorie(utilisateur.getDegreSecurite(),categorie, PageRequest.of(page, size));
    }

    @Override
    public Page<Documentation> getAllDocumentatioByTitre(Utilisateur utilisateur, String Titre, int size, int page) {
        return documentationRepository.findByTitre(utilisateur.getDegreSecurite(),Titre, PageRequest.of(page, size));
    }

    @Override
    public Page<Documentation> getAllDocumentatioByUtilisateurCreateur(Utilisateur utilisateur, String utilisateur_createur_name, int size, int page) {
        //TODO separer si 2 mots dans utilisateur_createur_name
        return documentationRepository.findByUtilisateurCreateur(utilisateur.getDegreSecurite(),utilisateur_createur_name,PageRequest.of(page, size));
    }

    @Override
    public Page<Documentation> getAllDocumentatioByFourAttribute(
            Utilisateur utilisateur, String utilisateur_createur_name, Type type, String Titre, Categorie categorie, int size, int page) {
        return documentationRepository.findByFourAttribute(utilisateur.getDegreSecurite(),
                type,categorie,Titre,utilisateur_createur_name, PageRequest.of(page, size));
    }

    @Override
    public Page<Documentation> getAllByUtilisateurCreateur(Utilisateur utilisateur, int size, int page) {
        return documentationRepository.findByUtilisateurCreateur(utilisateur, PageRequest.of(page,size));
    }

    @Override
    public Page<Documentation> getAllByUtilisateurCourant(Utilisateur utilisateur, int size, int page) {
        return documentationRepository.findAllByDegreSecurite(utilisateur.getDegreSecurite(), PageRequest.of(page, size));
    }

    @Override
    public Page<Documentation> getAllDocumentatioBySixAttribute(Utilisateur utilisateur, DegreSecurite degreSecurite, Langue langue, String utilisateur_createur_name, Type type, String Titre, Categorie categorie, int size, int page) {
        return documentationRepository.findBySixAttribute(degreSecurite,langue,utilisateur.getDegreSecurite(), type,categorie,Titre, utilisateur_createur_name, PageRequest.of(page, size));
    }
}
