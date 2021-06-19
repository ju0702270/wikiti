package com.rochez.wikiTI.controllers;

import com.google.gson.Gson;
import com.rochez.wikiTI.exception.DataIntergityException;
import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.service.*;
import com.rochez.wikiTI.utility.StringUtil;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author : Rochez Justin
 * @Creation : 02/06/2021
 * @Modification : 02/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour la gestion des utilisateurs par les administrateurs
 */

//TODO utiliser l'asynchrone pour la pagination ne fonctionne pas facilement, modifier la méthode soit en synchrone, soit là ou les filtre pourrons marche en asynchrone
@Controller
@RequestMapping("/admin")
public class UsermanagerController {
    final private String DEFAULTSIZE = "10";
    final private String DEFAULTPAGE = "0";

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    GradeService gradeService;
    @Autowired
    DegreSecuriteService degreSecuriteService;
    @Autowired
    FonctionService fonctionService;
    @Autowired
    RoleService roleService;

    /**
     * Méthode de recherche sur tout les attributs des utilisateurs.
     * @param page : Integer, le numéro de la page sur laquelle se trouve l'utilisateur
     * @param size : Integer, la taille de la pagination souhaitée par l'utilisateur
     * @param searchValue : String, la chaine de caractère sur laquelle faire la recheche
     * @param model
     * @return String, la vue fragmentée, mise à jour du tableau des utilisateurs.
     */
    @GetMapping("/searchUtilisateur")
    public String searchUtilsateur(@RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                                 @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                                 @RequestParam(name= "searchValue") String searchValue,
                                 Model model){
        Page<Utilisateur> pageUsers =utilisateurService.getSearchUtilisateurParPage(searchValue,page,size);
        generateModel(model,pageUsers,page,size);
        return "usermanager:: tbodyUserManager";
    }//fin searchUtilsateur

    /**
     * Méthode pour épargner des lignes
     * @param model
     * @param pageUsers : la page avec le liste des utilisateurs
     * @param page: int la page courante
     * @param size: int la taille du tableau souhaitée par l'utilisateur
     */
    private void generateModel(Model model, Page<Utilisateur> pageUsers, int page, int size){
        model.addAttribute("size", size);
        model.addAttribute("pages", new int[pageUsers.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("previous", (page > 0)? page-1: page);
        model.addAttribute("next", (page < pageUsers.getTotalPages()-1 )? page+1: page);
        model.addAttribute("users", pageUsers);
    }//Fin generatModel

    /**
     * Méthode de chargement de la page usermanager avec la pagination des utilisateurs
     * @param page : Integer, le numéro de la page sur laquelle se trouve l'utilisateur
     * @param size : Integer, la taille de la pagination souhaitée par l'utilisateur
     * @param model
     * @return String, usermanager.html chargé avec la pagination des utilisateurs
     */
    @GetMapping("/usermanage")
    public String getUserManager(@RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                                 @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                                 Model model){
        Page<Utilisateur> pageUsers =utilisateurService.getAllUtilisateurParPage(page,size);
        generateModel( model,pageUsers,page,size);
        return "usermanager";
    }//fin getUserManager


    /**
     * Méthode de chargement du model en vue de créée un nouvel utilisateur.
     * Cette méthode fonctionne avec la fenêtre modal 'usermanager:: updateModal'
     * @param model
     * @return String, la vue fragmentée, mise à jour de la fenetre modal updateModal.
     */
    @GetMapping("/ajouterUtilisateur")
    public String ajouterUtilisateur(Model model){
        Utilisateur u = new Utilisateur();
        model.addAttribute("grades",gradeService.getAllGrade());
        model.addAttribute("fonctions",fonctionService.getAllFonction());
        model.addAttribute("degreSecurites",degreSecuriteService.getAllDegreSecurite());
        model.addAttribute("user", u);
        return "usermanager:: updateModal";

    }//fin ajouter un attribut

    /**
     * Méthode de chargement du model en vue de modifier un  utilisateur.
     * Cette méthode fonctionne avec la fenêtre modal 'usermanager:: updateModal'
     * @param model
     * @return String, la vue fragmentée, mise à jour de la fenetre modal updateModal.
     */
    @GetMapping("/modifierUtilisateur")
    public String updateUtilisateur(@RequestParam("matricule") String matricule,Model model){
        Utilisateur u =utilisateurService.getUtilisateur(matricule);
        model.addAttribute("grades",gradeService.getAllGrade());
        model.addAttribute("fonctions",fonctionService.getAllFonction());
        model.addAttribute("degreSecurites",degreSecuriteService.getAllDegreSecurite());
        model.addAttribute("user", u);
        model.addAttribute("mode", "edit");
        return "usermanager:: updateModal";
    }//fin updateUtilisateur

    /**
     * Méthode de création d'un utilisateur en asynchrone
     * @param data : les données du formulaire à traiter, il n'y a pas d'utilisation de la validation ainsi que les Bindingresult ici car
     *             les données sont chargée en ajax. De plus utiliser thymeleaf en asynchrone ne permet pas facilement de charger les données de jointure
     * @param model
     * @return ResponseEntity<String>: une Response avec un status et un message.
     */
    @RequestMapping(value= "saveUtilisateur", method= RequestMethod.POST)
    public ResponseEntity<String> saveUtilisateur(@RequestBody String data, Model model){
        try {
            Utilisateur utilisateur;
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Gson g = new Gson();
            Map<String, Object> relation = g.fromJson(data, Map.class);
            if (relation.get("id") != null){
                utilisateur = utilisateurService.getUtilisateur(Long.parseLong( relation.get("id").toString() ));
            }else{ // création d'un nouvel utilisateur
                utilisateur = new Utilisateur(relation.get("matricule").toString(),relation.get("nom").toString(),relation.get("prenom").toString(),
                        relation.get("mail").toString(),true,null,null,null,null);
            }

            /* validation back-end */
            Set<ConstraintViolation<Utilisateur>> constraintViolations = validator.validate(utilisateur);
            if (constraintViolations.size() > 0 ) {
                String error = StringUtil.bundle("error.nonvalid_arg")+":</br>";
                for (ConstraintViolation<Utilisateur> contraintes : constraintViolations) {
                    error+=contraintes.getPropertyPath()+"</br>";
                }
                return new ResponseEntity<String>(error, HttpStatus.BAD_REQUEST);
            }
            /* fin de la validation */
            if( (boolean)relation.get("resetPass") )
            {
                utilisateur.setResetpass(true);
            }
            if (utilisateur.getRole() == null) utilisateur.setRole(roleService.getRole(2L));// attribution minimum du role USERAUTH
            //attribution d'un password vide pour les nouveaux utilisateurs
            if (utilisateur.getPassword() == null) utilisateur.setPassword(UtilisateurUtil.encodePassword(""));
            utilisateur.setGrade(gradeService.getGrade(Long.parseLong(relation.get("grade_id").toString())));
            utilisateur.setFonction(fonctionService.getFonction(Long.parseLong(relation.get("fonction_id").toString())));
            utilisateur.setDegreSecurite(degreSecuriteService.getDegreSecurite(Long.parseLong(relation.get("degre_securite_id").toString())));
            /* sauvegarde en base de données*/
            if (utilisateurService.saveUtilisateur(utilisateur) != null) {
                return new ResponseEntity<String>(StringUtil.bundle("message.success_user_saved"), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(StringUtil.bundle("error.unsaved"), HttpStatus.BAD_REQUEST);
            }
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return new ResponseEntity<>(StringUtil.bundle("error.duplicate")
                    , HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    StringUtil.bundle("error.unexpected"),
                    HttpStatus.BAD_REQUEST);
        }
    }//fin saveUtilisateur

    /**
     * Méthode de suppression d'un utilisateur
     * @param id : Long  => l'identifiant de l'utilisateur à supprimer.
     * @return ResponseEntity<String>: une réponse chaine de caractère avec une status de réponse HTTP
     *                              - return HttpStatus.OK si la requete et la suppression s'est déroulée convenablement
     *                              - return Http.Status.BAD_REQUEST si la requete a rencontré un problème + retourne un message d'erreur
     */
    @PostMapping("/supprimerUtilisateur")
    public ResponseEntity<String> supprimerUtilisateur(@Param("id") Long id){
        try {
            utilisateurService.deleteUtilisateurByID(id);
            return new ResponseEntity<>(StringUtil.bundle("message.success_user_deleted"),HttpStatus.OK);
        }catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(StringUtil.bundle("error.notdeleteuser")
                    , HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(
                    StringUtil.bundle("error.unexpected"),
                    HttpStatus.BAD_REQUEST);
        }
    }//fin supprimerUtilisateur

    /**
     * Refresh asynchrone du tableau de gestion des utilisateurs
     * @param model
     * @return String, la vue fragmentée, mise à jour du tableau des utilisateurs.
     */
    @GetMapping("/refreshUser")
    public String refreshUser(@RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                              @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                              Model model){
        Page<Utilisateur> pageUsers =utilisateurService.getAllUtilisateurParPage(page,size);
        generateModel( model,pageUsers,page,size);
        return "usermanager:: tbodyUserManager";
    }//fin refreshUser
}
