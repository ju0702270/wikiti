package com.rochez.wikiTI.repository;

import com.rochez.wikiTI.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.HashSet;
import java.util.List;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Repository / DAO de la documentation
 */
public interface DocumentationRepository extends JpaRepository<Documentation, Long> {

    /**
     * Méthode de recherche de plusieurs documentation dans la base de donnée
     * cette méthode recherche les documentations selon le degré de sécurité, qu'il ne soit pas plus haut que celui fourni
     * et selon une valeur de recherche qui peut se trouver dans n'importe quel attributs de la documentation
     * @param degreSecurite : le degré de sécurité maximum des documentations à récuperer
     * @param searchValue : la valeur à chercher
     * @return HashSet<Documentation> une liste non redondante des documentations souhaitée
     */
    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "(d.titre like %:searchValue% or " +
            "d.version like %:searchValue% or " +
            "d.scope like %:searchValue% or " +
            "d.degreSecurite.libelle like %:searchValue% or " +
            "d.langue.code like %:searchValue% or " +
            "d.langue.libelle like %:searchValue% or " +
            "d.type.libelle like %:searchValue% or " +
            "d.utilisateur_createur.matricule like %:searchValue% or " +
            "d.utilisateur_createur.nom like %:searchValue% or " +
            "d.utilisateur_createur.prenom like %:searchValue% or " +
            "d in ( select sc.documentation from SectionContenu sc where sc.html like %:searchValue% or " +
            "sc.titre like %:searchValue% ) or " +
            "c.libelle like %:searchValue% " +
            ")")
    HashSet<Documentation> findByAllAttribute(@Param("degreSecurite") DegreSecurite degreSecurite,
                                                  @Param("searchValue") String searchValue);

    /**
     * Méthode de recherche de plusieurs documentation dans la base de donnée
     * cette méthode recherche les documentations selon le degré de sécurité, qu'il ne soit pas plus haut que celui fourni
     * et selon une valeur de recherche qui peut se trouver dans n'importe quel attributs de la documentation
     * @param degreSecurite : le degré de sécurité maximum des documentations à récuperer
     * @param searchValue : la valeur à chercher
     * @return Page<Documentation> une liste non redondante des documentations souhaitée
     */
    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "(d.titre like %:searchValue% or " +
            "d.version like %:searchValue% or " +
            "d.scope like %:searchValue% or " +
            "d.degreSecurite.libelle like %:searchValue% or " +
            "d.langue.code like %:searchValue% or " +
            "d.langue.libelle like %:searchValue% or " +
            "d.type.libelle like %:searchValue% or " +
            "d.utilisateur_createur.matricule like %:searchValue% or " +
            "d.utilisateur_createur.nom like %:searchValue% or " +
            "d.utilisateur_createur.prenom like %:searchValue% or " +
            "d in ( select sc.documentation from SectionContenu sc where sc.html like %:searchValue% or " +
            "sc.titre like %:searchValue% ) or " +
            "c.libelle like %:searchValue% " +
            ")")
    Page<Documentation> findByAllAttribute(@Param("degreSecurite") DegreSecurite degreSecurite,
                                           @Param("searchValue") String searchValue,
                                           Pageable pageable);

    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id}")
    HashSet<Documentation> findAllByDegreSecurite(@Param("degreSecurite") DegreSecurite degreSecurite);

    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id}")
    Page<Documentation> findAllByDegreSecurite(@Param("degreSecurite") DegreSecurite degreSecurite, Pageable pageable);


    @Query(value = "select d from Documentation d " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "d.type = :type ")
    HashSet<Documentation> findByType(@Param("degreSecurite") DegreSecurite degreSecurite,
                                      @Param("type") Type type);

    @Query(value = "select d from Documentation d " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "d.type = :type ")
    Page<Documentation> findByType(@Param("degreSecurite") DegreSecurite degreSecurite,
                                      @Param("type") Type type, Pageable pageable);

    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "c = :categorie ")
    HashSet<Documentation> findByCategorie(@Param("degreSecurite") DegreSecurite degreSecurite,
                                            @Param("categorie") Categorie categorie);

    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "c = :categorie ")
    Page<Documentation> findByCategorie(@Param("degreSecurite") DegreSecurite degreSecurite,
                                           @Param("categorie") Categorie categorie,
                                        Pageable pageable);


    @Query(value = "select d from Documentation d " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "d.titre like %:titre% ")
    HashSet<Documentation> findByTitre(@Param("degreSecurite") DegreSecurite degreSecurite,
                                        @Param("titre") String titre );
    @Query(value = "select d from Documentation d " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "d.titre like %:titre% ")
    Page<Documentation> findByTitre(@Param("degreSecurite") DegreSecurite degreSecurite,
                                    @Param("titre") String titre, Pageable pageable );


    @Query(value = "select d from Documentation d " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and (" +
            "d.utilisateur_createur.nom like %:utilisateur_createur% or " +
            "d.utilisateur_createur.prenom like %:utilisateur_createur% )")
    HashSet<Documentation> findByUtilisateurCreateur(@Param("degreSecurite") DegreSecurite degreSecurite,
                                                     @Param("utilisateur_createur") String utilisateur_createur);
    @Query(value = "select d from Documentation d " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and (" +
            "d.utilisateur_createur.nom like %:utilisateur_createur% or " +
            "d.utilisateur_createur.prenom like %:utilisateur_createur% )")
    Page<Documentation> findByUtilisateurCreateur(@Param("degreSecurite") DegreSecurite degreSecurite,
                                                     @Param("utilisateur_createur") String utilisateur_createur,
                                                  Pageable pageable);

    /**
     * Méthode de recherche de plusieurs documentation dans la base de donnée
     * cette méthode recherche les documentations selon le degré de sécurité, qu'il ne soit pas plus haut que celui fourni
     * et selon une valeur de recherche qui peut se trouver dans 4 attributs spécifique de la documentation
     * @param degreSecurite : le degré de sécurité maximum des documentations à récuperer
     * @param type : le type de documentation que l'on souhaite
     * @param categorie : la catégorie de documentation que l'on souhaite
     * @param titre :le titre de la documentation que l'on souhaite
     * @param utilisateur_createur : le nom ou prénom de l'utilisateur_createur de la documentation que l'on souhaite
     * @return HashSet<Documentation> une liste non redondante des documentations souhaitée
     */
    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "d.type = :type and " +
            "c = :categorie and " +
            "d.titre like %:titre% and " +
            "(d.utilisateur_createur.nom like %:utilisateur_createur% or "  +
            "d.utilisateur_createur.prenom like %:utilisateur_createur%) ")
    HashSet<Documentation> findByFourAttribute(@Param("degreSecurite") DegreSecurite degreSecurite,
                                               @Param("type") Type type,
                                               @Param("categorie") Categorie categorie,
                                               @Param("titre") String titre,
                                               @Param("utilisateur_createur") String utilisateur_createur);



    /**
     * Méthode de recherche de plusieurs documentation dans la base de donnée
     * cette méthode recherche les documentations selon le degré de sécurité, qu'il ne soit pas plus haut que celui fourni
     * et selon une valeur de recherche qui peut se trouver dans 6 attributs spécifique de la documentation
     * @param degreSecurite : le degré de sécurité maximum des documentations à récuperer
     * @param type : le type de documentation que l'on souhaite
     * @param categorie : la catégorie de documentation que l'on souhaite
     * @param titre :le titre de la documentation que l'on souhaite
     * @param utilisateur_createur : le nom ou prénom de l'utilisateur_createur de la documentation que l'on souhaite
     * @Param searchDegreSecurite : le degré de sécurité de la documentation que l'on souhaite
     * @Param langue : la langue de la documentation que l'on souhaite
     * @return HashSet<Documentation> une liste non redondante des documentations souhaitée
     */
    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            " d.langue = :langue and " +
            " d.degreSecurite = :searchDegreSecurite and "+
            "d.type = :type and " +
            "c = :categorie and " +
            "d.titre like %:titre% and " +
            "(d.utilisateur_createur.nom like %:utilisateur_createur% or "  +
            "d.utilisateur_createur.prenom like %:utilisateur_createur%) ")
    HashSet<Documentation> findBySixAttribute(@Param("searchDegreSecurite") DegreSecurite searchDegreSecurite,
            @Param("langue") Langue langue,
            @Param("degreSecurite") DegreSecurite degreSecurite,
                                               @Param("type") Type type,
                                               @Param("categorie") Categorie categorie,
                                               @Param("titre") String titre,
                                               @Param("utilisateur_createur") String utilisateur_createur);


    /**
     * Méthode de recherche de plusieurs documentation dans la base de donnée
     * cette méthode recherche les documentations selon le degré de sécurité, qu'il ne soit pas plus haut que celui fourni
     * et selon une valeur de recherche qui peut se trouver dans 6 attributs spécifique de la documentation
     * @param degreSecurite : le degré de sécurité maximum des documentations à récuperer
     * @param type : le type de documentation que l'on souhaite
     * @param categorie : la catégorie de documentation que l'on souhaite
     * @param titre :le titre de la documentation que l'on souhaite
     * @param utilisateur_createur : le nom ou prénom de l'utilisateur_createur de la documentation que l'on souhaite
     * @Param searchDegreSecurite : le degré de sécurité de la documentation que l'on souhaite
     * @Param langue : la langue de la documentation que l'on souhaite
     * @return Page<Documentation> une liste non redondante des documentations souhaitée
     */
    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            " d.langue = :langue and " +
            " d.degreSecurite = :searchDegreSecurite and "+
            "d.type = :type and " +
            "c = :categorie and " +
            "d.titre like %:titre% and " +
            "(d.utilisateur_createur.nom like %:utilisateur_createur% or "  +
            "d.utilisateur_createur.prenom like %:utilisateur_createur%) ")
    Page<Documentation> findBySixAttribute(@Param("searchDegreSecurite") DegreSecurite searchDegreSecurite,
                                              @Param("langue") Langue langue,
                                              @Param("degreSecurite") DegreSecurite degreSecurite,
                                              @Param("type") Type type,
                                              @Param("categorie") Categorie categorie,
                                              @Param("titre") String titre,
                                              @Param("utilisateur_createur") String utilisateur_createur,
                                           Pageable pageable);


    /**
     * Méthode de recherche de plusieurs documentation dans la base de donnée
     * cette méthode recherche les documentations selon le degré de sécurité, qu'il ne soit pas plus haut que celui fourni
     * et selon une valeur de recherche qui peut se trouver dans 4 attributs spécifique de la documentation
     * @param degreSecurite : le degré de sécurité maximum des documentations à récuperer
     * @param type : le type de documentation que l'on souhaite
     * @param categorie : la catégorie de documentation que l'on souhaite
     * @param titre :le titre de la documentation que l'on souhaite
     * @param utilisateur_createur : le nom ou prénom de l'utilisateur_createur de la documentation que l'on souhaite
     * @return Page<Documentation> une liste non redondante des documentations souhaitée
     */
    @Query(value = "select DISTINCT d from Documentation d JOIN d.categories c " +
            "where d.degreSecurite.id <= ?#{#degreSecurite.id} and " +
            "d.type = :type and " +
            "c = :categorie and " +
            "d.titre like %:titre% and " +
            "(d.utilisateur_createur.nom like %:utilisateur_createur% or "  +
            "d.utilisateur_createur.prenom like %:utilisateur_createur%) ")
    Page<Documentation> findByFourAttribute(@Param("degreSecurite") DegreSecurite degreSecurite,
                                               @Param("type") Type type,
                                               @Param("categorie") Categorie categorie,
                                               @Param("titre") String titre,
                                               @Param("utilisateur_createur") String utilisateur_createur,
                                            Pageable pageable);

    @Query(value = "select d from Documentation d where d.utilisateur_createur = ?1" +
            " order by d.dateCreation desc")
    HashSet<Documentation> findByUtilisateurCreateur(Utilisateur utilisateur);
    @Query(value = "select d from Documentation d where d.utilisateur_createur = ?1" +
            " order by d.dateCreation desc")
    Page<Documentation> findByUtilisateurCreateur(Utilisateur utilisateur, Pageable pageable);

}
