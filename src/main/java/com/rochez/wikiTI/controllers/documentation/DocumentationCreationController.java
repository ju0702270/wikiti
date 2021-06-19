package com.rochez.wikiTI.controllers.documentation;


import com.rochez.wikiTI.model.*;
import com.rochez.wikiTI.service.*;
import com.rochez.wikiTI.service.fileStorage.FilesStorageService;
import com.rochez.wikiTI.utility.StringUtil;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour la creation et modification de documentation
 */
@Controller
@RequestMapping("/auth")
public class DocumentationCreationController {
    @Autowired
    LangueService langueService;
    @Autowired
    DegreSecuriteService degreSecuriteService;
    @Autowired
    TypeService typeService;
    @Autowired
    CategorieService categorieService;
    @Autowired
    DocumentationService documentationService;
    @Autowired
    FilesStorageService storageService;

    /**
     * Méthode pour construire la page de base du formulaire de création de documentation
     * cette méthode récupere sa page depuis une méthode privée.
     * @param documentation : L'objet documentation rempli s'il est disponible dans en pâramètre, sinon une documentation vide
     * @param model
     * @return vue documentation_creation.html
     */
    @GetMapping("/docCreation")
    public String getDocCreation(@ModelAttribute("documentation") Documentation documentation, Model model) {
        Utilisateur utilisateur = UtilisateurUtil.getCurrentUtilisateur();
        if (utilisateur == null) return "redirect:/accessDenied";
        model.addAttribute("user",utilisateur);
        if (documentation.isEmpty()){//si nouvelle documentation
            return loadDocPage(utilisateur,new Documentation(), model);
        }else { // si documentation à modifier
            return loadDocPage(utilisateur,documentation, model);
        }
    }//fin getDocCreation

    /**
     * Méthode pour récuperer une documentation existante afin de constuire le formulaire de modification
     * cette méthode récupere sa page depuis une méthode privée.
     * @param documentation : L'objet documentation à afficher
     * @param model
     * @Param idDoc : Long, l'identifiant de la documentation, celui-ci n'est pas géré pas l'objet documentation thymeleaf
     * @return vue documentation_creation.html
     */
    @GetMapping("/docCreation/{idDoc}")
    public String getDocModification(@ModelAttribute("documentation") Documentation documentation,@PathVariable("idDoc") Long idDoc
            , Model model) throws ParseException {
        Utilisateur utilisateur = UtilisateurUtil.getCurrentUtilisateur();
        if (utilisateur == null) return "redirect:/accessDenied";
        if (documentation.isEmpty()){
            documentation = documentationService.getDocumentation(idDoc);
        }
        //avec le format String de date définie dans Documentation, la récupération de date pose problème, il faut donc s'assurer que la date de création n'est pas perdue
        documentation.setDateCreation(documentationService.getDocumentation(idDoc).getDateCreation());
        return loadDocPage(utilisateur,documentation, model);
    }// fin getDocModification

    /**
     * Méthode de construction du model ainsi que de la documentation et la gestion des erreurs
     * Cette méthode doit être appelée comme return dans les méthode de ce controlleur qui doivent renvoyer lavue documentation_creation.html
     * @param currentUser : l'utilisateur courant
     * @param documentation : la documentation a traiter
     * @param model
     * @return vue documentation_creation.html
     */
    private String loadDocPage(Utilisateur currentUser,Documentation documentation, Model model){
        model.addAttribute("user",currentUser);
        model.addAttribute("langues", langueService.getAllLangue());
        model.addAttribute("degreSecurites", degreSecuriteService.getAllDegreSecurite());
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("listCategorie", categorieService.getAllCategorie());
        if (documentation.isEmpty()){ /* Si nouvelle documentation */
            SectionContenu sectionContenu= new SectionContenu();
            List<Categorie> categories = new ArrayList<>();
            categories.add(new Categorie());
            documentation.setCategories(categories);
            documentation.getSectionContenus().add(sectionContenu);
        }
        model.addAttribute("documentation", documentation);
        //Gestion des erreurs
        BindingResult bindingResult = (BindingResult) model.getAttribute("errors");
        if (bindingResult != null && bindingResult.hasErrors()){
            HashMap<String,String> errors = new HashMap<>();
            for ( ObjectError error : bindingResult.getAllErrors()) {
                errors.put( ((FieldError) error).getField(), error.getDefaultMessage());
            }
            model.addAttribute("errors",errors);
            return "documentation_creation";
        }//fin Gestion des erreurs
        return "documentation_creation";
    }//fin loadDocPage

    /**
     * Méthode d'ajout d'une catégorie dans la documentation afin de l'afficher dans le formulaire
     * @param documentation : la documentation dans laquelle ajouter la catégorie
     * @param idDoc : Long, l'identifiant de la documentation, celui-ci n'est pas géré pas l'objet documentation thymeleaf
     * @param redirectAttributes : Les attributs à rediriger vers une autre url
     * @return la vue affichée par la redirection vers docCreation (nouveau document ou modification )
     */
    @PostMapping("/addCategorie")
    public String addCategorie(@ModelAttribute("documentation") Documentation documentation, @Param("idDoc") Long idDoc
            ,RedirectAttributes redirectAttributes) {
        documentation.getCategories().add(new Categorie());
        redirectAttributes.addFlashAttribute("documentation",documentation);
        if (idDoc != -1 ) { // si nouvelle documentation
            documentation.setId(idDoc);
            return "redirect:docCreation/"+idDoc;
        }
        return "redirect:docCreation";
    }// fin addCategorie

    /**
     * Méthode de retrait d'une catégorie dans la documentation afin de l'afficher dans le formulaire
     * @param documentation : la documentation dans laquelle retirer la catégorie
     * @param idDoc : Long, l'identifiant de la documentation, celui-ci n'est pas géré pas l'objet documentation thymeleaf
     * @param redirectAttributes : Les attributs à rediriger vers une autre url
     * @return la vue affichée par la redirection vers docCreation (nouveau document ou modification )
     */
    @PostMapping("/minusCategorie")
    public String minusCategorie(@ModelAttribute("documentation") Documentation documentation,
                                 @Param("idDoc") Long idDoc,RedirectAttributes redirectAttributes){
        documentation.getCategories().remove(documentation.getCategories().size()-1);
        redirectAttributes.addFlashAttribute("documentation",documentation);
        if (idDoc != -1 ) {
            documentation.setId(idDoc);
            return "redirect:docCreation/"+idDoc;
        }
        return "redirect:docCreation";
    }//fin minusCategorie

    /**
     * Méthode d'ajout d'une SectionContenu dans la documentation afin de l'afficher dans le formulaire
     * @param documentation : la documentation dans laquelle ajouter la SectionContenu
     * @param idDoc : Long, l'identifiant de la documentation, celui-ci n'est pas géré pas l'objet documentation thymeleaf
     * @param redirectAttributes : Les attributs à rediriger vers une autre url
     * @return la vue affichée par la redirection vers docCreation (nouveau document ou modification )
     */
    @PostMapping("/addSection")
    public String addSection(@ModelAttribute("documentation") Documentation documentation, @Param("idDoc") Long idDoc
            , RedirectAttributes redirectAttributes) {
        SectionContenu sectionContenu = new SectionContenu();
        documentation.getSectionContenus().add(sectionContenu);
        redirectAttributes.addFlashAttribute("documentation",documentation);
        if (idDoc != -1 ) {
            documentation.setId(idDoc);
            return "redirect:docCreation/"+idDoc;
        }
        return "redirect:docCreation";
    }//fin addSection

    /**
     * Méthode de retrait d'une SectionContenu dans la documentation afin de l'afficher dans le formulaire
     * @param documentation : la documentation dans laquelle retirer la SectionContenu
     * @param idDoc : Long, l'identifiant de la documentation, celui-ci n'est pas géré pas l'objet documentation thymeleaf
     * @param redirectAttributes : Les attributs à rediriger vers une autre url
     * @return la vue affichée par la redirection vers docCreation (nouveau document ou modification )
     */
    @PostMapping("/minusSection")
    public String minusSection(@ModelAttribute("documentation") Documentation documentation,
                               @Param("idDoc") Long idDoc, RedirectAttributes redirectAttributes) {
        documentation.getSectionContenus().remove(documentation.getSectionContenus().size()-1);

        redirectAttributes.addFlashAttribute("documentation",documentation);
        if (idDoc != -1 ) {
            documentation.setId(idDoc);
            return "redirect:docCreation/"+idDoc;
        }
        return "redirect:docCreation";
    }// fin minusSection


    /**
     * Méthode de sauvegarde de la documentation.
     * Cette méthode fera une validation simple des données avant de sauvegarder. Si des données ne sont pas valide alors certains
     * elle renverra des erreurs pour le front end
     * @param documentation : la documentation venant du front-end à valider et enregistrer
     * @param bindingResult : Liste des erreurs potentiels
     * @param idDoc : l'id de la documentation, en effet th:field ne gère pas l'id de l'objet.
     * @param dateDoc : la date de creation ou modification de la documentation
     * @param redirectAttributes : Object des attributs à rediriger
     * @return la vue  redirect:docCreation
     * @throws ParseException
     */
    @PostMapping("/saveDoc")
    public String saveDoc(@Valid @ModelAttribute("documentation") Documentation documentation,BindingResult bindingResult,
                          @Param("idDoc") Long idDoc, @Param("dateDoc") String dateDoc, RedirectAttributes redirectAttributes) throws ParseException {
        Utilisateur utilisateur = UtilisateurUtil.getCurrentUtilisateur();
        if (utilisateur== null) return "redirect:/accessDenied";

        /* Eviter les doublons de catégorie */
        //TODO définir une méthode dans DOcumentationService afin d'éviter les doublons, ou utiliser HashSet
        List<Categorie> categories = new ArrayList<>();
        for (Categorie cat: documentation.getCategories()) {
            if ( !categories.contains(cat) ) categories.add(cat);
        }
        if (documentation.getCategories().size() != categories.size()) {
            FieldError error = new FieldError("Documentation", "categories", StringUtil.bundle("error.categorieDoublon"));
            bindingResult.addError(error);
        }
        documentation.setCategories(categories);
        /*gestion du degreSecurite à savoir qu'il est aussi géré directement dans le DocumentationService*/
        if (utilisateur.getDegreSecurite().getId() < documentation.getDegreSecurite().getId() ){
            FieldError error = new FieldError("Documentation","degreSecurite", StringUtil.bundle("error.degreSecurityToHigh"));
            bindingResult.addError(error);
        }
        /* Gestions des autres erreurs Utilisée depuis le model d'objet*/
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult);
            if (idDoc != -1) {
                documentation.setId(idDoc);
                return "redirect:docCreation/" + idDoc;
            }
            return "redirect:docCreation";
        }
        /* Redéfinition de la documentation */
        if(idDoc != -1) {//si update
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCreation = dateformat.parse(String.valueOf(dateDoc));
            documentation.setDateCreation(dateCreation);
            documentation.setId(idDoc);
            documentation.setDateModification(new Date());
            documentation.setUtilisateur_modifieur(utilisateur);
        }else{
            documentation.setDateCreation(new Date());
            documentation.setUtilisateur_createur(utilisateur);
        }
        documentation = documentationService.saveDocumentation(utilisateur.getDegreSecurite(),documentation);
        if (documentation == null) return "redirect:docCreation";
        return "redirect:docCreation/"+documentation.getId();
    }//fin saveDoc

    /**
     * Méthode d'upload d'une image depuis ckeditor
     * @param upload : MultipartFile, l'image à uploader // TODO permettre la récupération correcte de l'image
     * @return Une response JSON contenant les attributs JSONFileUpload
     */
    @RequestMapping(value="upload_ckeditor", method = RequestMethod.POST, produces = {
            MimeTypeUtils.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<JSONFileUpload> UploadCKEditor(@RequestParam("upload") MultipartFile upload){
        // Vérification de la validité de l'image
        List<String> listAutorizedFormat = Arrays.asList("image/jpeg","image/png","image/gif","image/bmp","image/webp","image/tiff");
        if (!listAutorizedFormat.contains(upload.getContentType())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);;
        try{
            String fileName = storageService.save(upload);
            return new ResponseEntity<JSONFileUpload>(new JSONFileUpload(upload.getOriginalFilename(),
                    "/uploads/"+fileName
            ),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }//fin UploadCKEditor
}
