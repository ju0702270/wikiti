package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.*;
import org.springframework.data.domain.Page;

import java.util.HashSet;
import java.util.List;

public interface DocumentationService {
    Documentation saveDocumentation(DegreSecurite degreSecurite, Documentation documentation);
    boolean deleteDocumentation(Documentation documentation);
    Documentation getDocumentation(Long id);
    List<Documentation> getAllDocumentation();
    Page<Documentation> getAllDocumentationByAttribute(Utilisateur utilisateur, String searchValue, int size, int page);
    HashSet<Documentation> getAllDocumentationByAttribute(Utilisateur utilisateur, String searchValue);
    HashSet<Documentation> getAllDocumentatioByType(Utilisateur utilisateur, Type type);
    HashSet<Documentation> getAllDocumentatioByCategorie(Utilisateur utilisateur, Categorie categorie);
    HashSet<Documentation> getAllDocumentatioByTitre(Utilisateur utilisateur, String Titre);
    HashSet<Documentation> getAllDocumentatioByUtilisateurCreateur(Utilisateur utilisateur, String utilisateur_createur_name);
    HashSet<Documentation> getAllDocumentatioByFourAttribute(Utilisateur utilisateur, String utilisateur_createur_name,
                                                             Type type, String Titre, Categorie categorie);
    HashSet<Documentation> getAllDocumentatioBySixAttribute(Utilisateur utilisateur, DegreSecurite degreSecurite, Langue langue
            , String utilisateur_createur_name, Type type, String Titre, Categorie categorie);
    HashSet<Documentation> getAllByUtilisateurCreateur(Utilisateur utilisateur);
    HashSet<Documentation> getAllByUtilisateurCourant(Utilisateur utilisateur);

    Page<Documentation> getAllDocumentatioByType(Utilisateur utilisateur, Type type, int size, int page);
    Page<Documentation> getAllDocumentatioByCategorie(Utilisateur utilisateur, Categorie categorie, int size, int page);
    Page<Documentation> getAllDocumentatioByTitre(Utilisateur utilisateur, String Titre, int size, int page);
    Page<Documentation> getAllDocumentatioByUtilisateurCreateur(Utilisateur utilisateur, String utilisateur_createur_name, int size, int page);
    Page<Documentation> getAllDocumentatioByFourAttribute(Utilisateur utilisateur, String utilisateur_createur_name,
                                                             Type type, String Titre, Categorie categorie, int size, int page);
    Page<Documentation> getAllByUtilisateurCreateur(Utilisateur utilisateur, int size, int page);
    Page<Documentation> getAllByUtilisateurCourant(Utilisateur utilisateur, int size, int page);

    Page<Documentation> getAllDocumentatioBySixAttribute(Utilisateur utilisateur, DegreSecurite degreSecurite, Langue langue
            , String utilisateur_createur_name, Type type, String Titre, Categorie categorie, int size, int page);

}
