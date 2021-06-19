package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UtilisateurService {
    Utilisateur saveUtilisateur(Utilisateur u);
    boolean deleteUtilisateur(Utilisateur u);
    boolean deleteUtilisateurByID(Long id);
    boolean deleteUtilisateurByMatricule(String matricule);
    Utilisateur getUtilisateur(String matricule);
    Utilisateur getUtilisateur(Long id);
    List<Utilisateur> getAllUtilisateur();
    Page<Utilisateur> getAllUtilisateurParPage(int page, int size);
    Utilisateur findUtilisateurByMail(String mail);
    List<Utilisateur> findByAllAttribute(String searchValue);
    Page<Utilisateur> getSearchUtilisateurParPage(String seachValue,int page, int size);


}
