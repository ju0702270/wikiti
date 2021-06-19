package com.rochez.wikiTI.controllers;

import com.rochez.wikiTI.exception.ErrorMsgException;
import com.rochez.wikiTI.model.*;
import com.rochez.wikiTI.service.CategorieService;
import com.rochez.wikiTI.service.DegreSecuriteService;
import com.rochez.wikiTI.service.DocumentationService;
import com.rochez.wikiTI.service.TypeService;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @Author : Rochez Justin
 * @Creation : 17/06/2021
 * @Modification : 17/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour les recherches de documentation
 */
@Controller
public class MainController {
    @Autowired
    TypeService typeService;
    @Autowired
    CategorieService categorieService;
    @Autowired
    DocumentationService documentationService;
    @Autowired
    DegreSecuriteService degreSecuriteService;
    private final int SIZE = 10;

    /**
     * Méthode de base pour afficher la page index, il s'agit de la page principal de recherche de documentation
     * @param model
     * @return vue index.html
     */
    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public String getIndex(Model model)  {
        Utilisateur utilisateur = UtilisateurUtil.getCurrentUtilisateur();
        if (utilisateur != null && utilisateur.isResetpass()) return "redirect:/changePass";
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("categories",categorieService.getAllCategorie());
        return "index";
    }//fin getIndex

    /**
     * Méthode de recherche de documentation sur un attribut non connu de la documentation
     * C'est une méthode de recherche général
     * @param searchValueDoc : String, la valeur à rechercher parmis tout les attributs de l'object Documentation
     * @param model
     * @param page : la page courante
     * @param lang : la langue courante
     * @return vue index.html
     */
    @GetMapping("/searchDoc")
    public String search(@RequestParam("searchValueDoc") String searchValueDoc, Model model,
                         @RequestParam(name="page", defaultValue = "0") int page,
                         @RequestParam(name = "lang",defaultValue = "") String lang
                         ){
        if (!lang.equals("")) LocaleContextHolder.setLocale(new Locale(lang)); // gestion de la langue
        Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
        if (currentUser == null ){
            currentUser = new Utilisateur();
            currentUser.setDegreSecurite(degreSecuriteService.getDegreSecurite(1L));//sécurité minimum
        }
        Page<Documentation> documentationPage=documentationService.getAllDocumentationByAttribute(
                currentUser,searchValueDoc,SIZE, page);
        generateModel(model,page,documentationPage);
        model.addAttribute("searchValueDoc",searchValueDoc);
        return "index";
    }//fin search

    /**
     * Méthode privé pour generer le model et eviter trop de ligne de code
     * @param model : le model à remplir
     * @param page : la page courante
     * @param documentationPage : la liste page des documentations a mettre dans le model
     */
    private void generateModel (Model model, int page, Page<Documentation> documentationPage){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("categories",categorieService.getAllCategorie());
        model.addAttribute("pages", new int[documentationPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("previous", (page > 0)? page-1: page);
        model.addAttribute("next", (page < documentationPage.getTotalPages()-1 )? page+1: page);
        model.addAttribute("result", documentationPage.getTotalElements());
        model.addAttribute("documentations", documentationPage);
    }//fin generateModel


    /**
     * Méthode de recherche de documentation sur des attributs connu de la documentation
     *      * C'est une méthode de recherche spécifique
     * @param docType : Type, le type de la documentation recherchée
     * @param docTitre : String, le titre like de la documentation recherchée
     * @param docAuteur : String, le nom ou prénom like de l'auteur de la documentation recherchée
     * @param docCategorie : Categorie, une catégorie spécifique à la documentation recherchée
     * @param page : la page courante
     * @param lang : la langue désirée
     * @param model
     * @return la vue index.html
     */
    @GetMapping("/searchAdvanced")
    public String searchAdvanced(@RequestParam("docType") Type docType,
                         @RequestParam("docTitre") String docTitre,
                         @RequestParam("docAuteur") String docAuteur,
                         @RequestParam("docCategorie")Categorie docCategorie,
                         @RequestParam(name="page", defaultValue = "0") int page,
                         @RequestParam(name = "lang",defaultValue = "") String lang,
             Model model) {
        if (!lang.equals("")) LocaleContextHolder.setLocale(new Locale(lang));//gestion de la langue
        Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
        if (currentUser == null ){
            currentUser = new Utilisateur();
            currentUser.setDegreSecurite(degreSecuriteService.getDegreSecurite(1L));//sécurité minimum
        }
        Page<Documentation> documentationPage = documentationService.getAllDocumentatioByFourAttribute(
                currentUser,docAuteur,docType,docTitre,docCategorie, SIZE,page);
        generateModel(model,page,documentationPage);
        model.addAttribute("docType",docType);
        model.addAttribute("docTitre",docTitre);
        model.addAttribute("docAuteur",docAuteur);
        model.addAttribute("docCategorie",docCategorie);
        return "index";
    }//fin searchAdvanced












}
