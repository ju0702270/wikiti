package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Repository / DAO de l'utilisateur
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByMail (String mail);
    Utilisateur findByMatricule (String matricule);
    Utilisateur deleteByMatricule(String matricule);

    /**
     * Methode de recherche d'ulisateur sur tout ses attributs selons une valeur de recherche
     * @param searchValue  la valeur à chercher
     * @param pageable
     * @return Page<Utilisateur> une liste des utilisateur que l'on cherche
     */
    @Query(value = "select u from Utilisateur u " +
            "where u.matricule like %?1% or " +
            "u.prenom like %?1% or " +
            "u.nom like %?1% or " +
            "u.mail like %?1% or " +
            "u.grade.administratif like %?1% or " +
            "u.degreSecurite.libelle like %?1% or " +
            "u.fonction.libelle like %?1%")
    Page<Utilisateur> findByAllAttribute(String searchValue, Pageable pageable);

    /**
     * Methode de recherche d'ulisateur sur tout ses attributs selons une valeur de recherche
     * @param searchValue  la valeur à chercher
     * @return List<Utilisateur> une liste des utilisateur que l'on cherche
     */
    @Query(value = "select u from Utilisateur u " +
            "where u.matricule like %?1% or " +
            "u.prenom like %?1% or " +
            "u.nom like %?1% or " +
            "u.mail like %?1% or " +
            "u.grade.administratif like %?1% or " +
            "u.degreSecurite.libelle like %?1% or " +
            "u.fonction.libelle like %?1%")
    List<Utilisateur> findByAllAttribute(String searchValue);


}
