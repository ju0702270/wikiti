package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;


/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Implémentation du service
 */
@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur saveUtilisateur(Utilisateur u) {
        return utilisateurRepository.save(u);


    }

    @Override
    public boolean deleteUtilisateur(Utilisateur u) {
        utilisateurRepository.delete(u);
        return utilisateurRepository.findById(u.getId()).isPresent();
    }

    @Override
    public boolean deleteUtilisateurByID(Long id) {
        utilisateurRepository.deleteById(id);
        return utilisateurRepository.findById(id).isPresent();
    }

    @Override
    public boolean deleteUtilisateurByMatricule(String matricule) {
        utilisateurRepository.deleteByMatricule(matricule);
        return (this.getUtilisateur(matricule) == null);
    }

    @Override
    public Utilisateur getUtilisateur(String matricule) {
        return utilisateurRepository.findByMatricule(matricule);
    }

    @Override
    public Utilisateur getUtilisateur(Long id) {
        return utilisateurRepository.findById(id).get();
    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Page<Utilisateur> getAllUtilisateurParPage(int page, int size) {
        return utilisateurRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public Utilisateur findUtilisateurByMail(String mail) {
        return utilisateurRepository.findByMail(mail);
    }

    @Override
    public List<Utilisateur> findByAllAttribute(String searchValue) {
        return utilisateurRepository.findByAllAttribute(searchValue);
    }

    @Override
    public Page<Utilisateur> getSearchUtilisateurParPage(String seachValue, int page, int size) {
        return utilisateurRepository.findByAllAttribute(seachValue,PageRequest.of(page,size));
    }
}
