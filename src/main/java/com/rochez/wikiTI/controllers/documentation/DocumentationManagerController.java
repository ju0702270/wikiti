package com.rochez.wikiTI.controllers.documentation;


import com.rochez.wikiTI.model.*;
import com.rochez.wikiTI.service.*;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Locale;


/**
 * @Author : Rochez Justin
 * @Creation : 17/06/2021
 * @Modification : 17/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour la gestion de la documentation au niveau administrateur
 */
@Controller
@RequestMapping("/admin")
public class DocumentationManagerController {

    @Autowired
    TypeService typeService;
    @Autowired
    CategorieService categorieService;
    @Autowired
    DocumentationService documentationService;
    @Autowired
    DegreSecuriteService degreSecuriteService;
    @Autowired
    LangueService langueService;
    final private String DEFAULTSIZE = "10";
    final private String DEFAULTPAGE = "0";


    /**
     * Méthode de base pour afficher la page documentationmanager, il s'agit de la page principal de gestion de documentation
     * @param model
     * @param page : la page courante
     * @param size : la taille du tableau de résultats souhaitée par l'utilisateur
     * @return la vue documentationmanager.html
     */
    @RequestMapping(value = {"/docManager"}, method = RequestMethod.GET)
    public String getDocumentationManager(Model model,
                                          @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                                          @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size){
        try{
            Utilisateur currentUser = UtilisateurUtil.getCurrentUtilisateur();
            if (currentUser == null) return "/accessDenied"; //Spring sécurity fait déjà une vérification avant. ceci est une vérification supplémentaire
            Page<Documentation> documentationPage = documentationService.getAllByUtilisateurCourant(currentUser, size,page);
            generateModel(model,size,page, documentationPage);
            return "documentationmanager";
        }catch (Exception e){
            return "/error";
        }
    }//fin getDocumentationManager

    /**
     * Méthode privé pour generer le model et eviter trop de ligne de code
     * @param model : le model à remplir
     * @param size : la taille du tableau de résultats souhaitée par l'utilisateur
     * @param page : la page courante
     * @param documentationPage : la liste page des documentations a mettre dans le model
     */
    private void generateModel(Model model,int size, int page, Page<Documentation> documentationPage){
        model.addAttribute("types",typeService.getAllType());
        model.addAttribute("categories",categorieService.getAllCategorie());
        model.addAttribute("langues", langueService.getAllLangue());
        model.addAttribute("degreSecurites",degreSecuriteService.getAllDegreSecurite());
        model.addAttribute("size", size);
        model.addAttribute("pages", new int[documentationPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("previous", (page > 0)? page-1: page);
        model.addAttribute("next", (page < documentationPage.getTotalPages()-1 )? page+1: page);
        model.addAttribute("documentations", documentationPage);
    }//fin generateModel

    /**
     * Méthode de suppression d'une documentation
     * @param documentation : la documentation à supprimer
     * @param size : la taille du tableau de résultats souhaitée par l'utilisateur
     * @param page : la page courante
     * @param redirectAttributes : objet de redirection d'attribut, il est utilisé dans notre cas pour rediriger la page et size
     * @return redirection sur docManager
     */
    @GetMapping("/delete")
    public String deleteDoc( @RequestParam("doc") Documentation documentation,
                             @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                             @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                             RedirectAttributes redirectAttributes){
        try{
            redirectAttributes.addAttribute("page",page);
            redirectAttributes.addAttribute("size",size);
            documentationService.deleteDocumentation(documentation);
            return "redirect:docManager";
        }catch (Exception e){
            return "/error";
        }

    }// fin deleteDoc

    @GetMapping("/certify")
    public String certifyDoc( @RequestParam("doc") Documentation documentation,
                             @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                             @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                             RedirectAttributes redirectAttributes){
        try{
            Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
            if (currentUser == null) return "/accessDenied";
            redirectAttributes.addAttribute("page",page);
            redirectAttributes.addAttribute("size",size);
            documentation.setCertified(true);
            if (documentationService.saveDocumentation(currentUser.getDegreSecurite(),documentation) == null) return "/error";
            return "redirect:docManager";
        }catch (Exception e){
            return "/error";
        }
    }// fin certifyDoc

    /**
     * Méthode recherche de documentations à partir d'une valeur de recherche quelquoncque
     * @param searchValueDocManager : la valeur de recherche à chercher dans les différentes documentations
     * @param model
     * @param page : la page courante
     * @param size :: la taille du tableau de résultats souhaitée par l'utilisateur
     * @param lang : la langue courante
     * @return la vue documentationmanager.html
     */
    @GetMapping("/searchDocManager")
    public String searchDocManager(@RequestParam("searchValueDocManager") String searchValueDocManager, Model model,
                         @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                         @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                                   @RequestParam(name = "lang",defaultValue = "") String lang
    ){
        try{
            if (!lang.equals("")) LocaleContextHolder.setLocale(new Locale(lang));
            //TODO la gestion de la langue pour une application qui prend de l'ampleur ne devrai pas être gérée dans les search paramètre de l'url
            Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
            if (currentUser == null) return "/accessDenied";

            Page<Documentation> documentationPage=documentationService.getAllDocumentationByAttribute(
                    currentUser,searchValueDocManager,size, page);
            generateModel(model,size,page,documentationPage);
            model.addAttribute("searchValueDocManager",searchValueDocManager);
            return "documentationmanager";
        }catch (Exception e){
            return "/error";
        }
    }//fin searchDocManager


    /**
     * Méthode de recherche de documentions avec 6 paramètres
     * @param model
     * @param docType : le type de documentation que l'on cherche
     * @param docTitre : le titre like de la documentation
     * @param docAuteur : l'utilisateur_createur like de la documentation
     * @param docCategorie : la categorie de documentation que l'on cherche
     * @param docLangue : la langue de documentation que l'on cherche
     * @param docDegreSecurite : le degreSecurité de documentation que l'on cherche
     * @param page : la page courante
     * @param size :: la taille du tableau de résultats souhaitée par l'utilisateur
     * @param lang : la langue courante
     * @return la vue documentationmanager.html
     */
    @GetMapping("/searchAdvancedDocManger")
    public String searchAdvancedDocManger(Model model,
                                          @RequestParam("docType") Type docType,
                                          @RequestParam("docTitre") String docTitre,
                                          @RequestParam("docAuteur") String docAuteur,
                                          @RequestParam("docCategorie") Categorie docCategorie,
                                          @RequestParam("docLangue")Langue docLangue,
                                          @RequestParam("docDegreSecurite") DegreSecurite docDegreSecurite,
                                          @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                                          @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                                          @RequestParam(name = "lang",defaultValue = "") String lang
    ){
        if (!lang.equals("")) LocaleContextHolder.setLocale(new Locale(lang));//gestion de la langue
        Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
        if (currentUser == null) return "/accessDenied";
        Page<Documentation> documentationPage = documentationService.getAllDocumentatioBySixAttribute(currentUser,
                docDegreSecurite,docLangue,docAuteur,docType,docTitre,docCategorie,size,page);
        generateModel(model,size,page,documentationPage);
        model.addAttribute("docType",docType);
        model.addAttribute("docTitre",docTitre);
        model.addAttribute("docAuteur",docAuteur);
        model.addAttribute("docCategorie",docCategorie);
        model.addAttribute("docDegreSecurite",docDegreSecurite);
        model.addAttribute("docLangue",docLangue);
        return "documentationmanager";
    }//fin searchAdvancedDocManger
}
