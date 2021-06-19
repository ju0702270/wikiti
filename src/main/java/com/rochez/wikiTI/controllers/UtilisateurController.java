package com.rochez.wikiTI.controllers;

import com.rochez.wikiTI.model.Documentation;
import com.rochez.wikiTI.model.Fonction;
import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.repository.FonctionRepository;
import com.rochez.wikiTI.repository.GradeRepository;
import com.rochez.wikiTI.repository.UtilisateurRepository;
import com.rochez.wikiTI.security.MyUserDetails;
import com.rochez.wikiTI.service.DocumentationService;
import com.rochez.wikiTI.utility.StringUtil;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.Doc;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * @Author : Rochez Justin
 * @Creation : 02/06/2021
 * @Modification : 17/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour l'espace personnel de l'utilisateur
 */
@Controller
public class UtilisateurController {

    private static final String DEFAULTSIZE = "10";
    private static final String DEFAULTPAGE = "0";
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    FonctionRepository fonctionRepository;
    @Autowired
    DocumentationService documentationService;

    /**
     * Méthode d'affichage des informations personnel de l'utilisateur sur la vue espacepersonnel
     * @param model
     * @param page : la page courante
     * @param size : la taille du tableau souhaitée par l'utilisateur
     * @return la vue espacepersonnel.html
     */
    @GetMapping("/utilisateur")
    public String getUtilisateurInfo(Model model,
                                 @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                                @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size) {
        Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
        if (currentUser == null ) return "accesDenied";

        Page<Documentation> documentationPage = documentationService.getAllByUtilisateurCreateur(currentUser,size,page);

        model.addAttribute("grades", gradeRepository.findByOrderByNatoAsc());// récupération des grades pour remplir les select html
        model.addAttribute("fonctions", fonctionRepository.findAll());// récupération des grades pour remplir les select html
        model.addAttribute("size", size);
        model.addAttribute("pages", new int[documentationPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("previous", (page > 0)? page-1: page);
        model.addAttribute("next", (page < documentationPage.getTotalPages()-1 )? page+1: page);
        model.addAttribute("documentations",documentationPage);
        model.addAttribute("user", currentUser);
        return "espacepersonnel";
    }//fin getUtilisateurInfo


    /**
     * Méthode de suppression d'une documentation sur l'espace personnel de l'utilisateur
     * @param documentation :  la documentation à supprimer
     * @param page : la page courante
     * @param size : la taille du tableau souhaitée par l'utilisateur
     * @param redirectAttributes : les attributs a rediriger.
     * @return redirection vers /utilisater => la vue espacepersonnel.html
     */
    @GetMapping("/utilisateur/delete")
    public String deleteDoc( @RequestParam("doc") Documentation documentation,
                            @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                            @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                            RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("page",page);
        redirectAttributes.addAttribute("size",size);
        documentationService.deleteDocumentation(documentation);
        return "redirect:/utilisateur";
    }// fin deleteDoc

    /**
     * Méthode de mise à jour de la fonction de l'utilisateur courant
     * @param fonction : fonction à mettre à jour pour l'utilisateur courant
     * @param page : la page courante
     * @param size : la taille du tableau souhaitée par l'utilisateur
     * @param redirectAttributes : les attributs a rediriger.
     * @return redirection vers /utilisater => la vue espacepersonnel.html
     */
    @PostMapping("/updateFonction")
    public String updateFonction(@RequestParam("fonction") Fonction fonction,
                                 @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                                 @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
             RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("page",page);
        redirectAttributes.addAttribute("size",size);

        Utilisateur utilisateur= UtilisateurUtil.getCurrentUtilisateur();
        if (utilisateur == null ) return "redirect:/accessDenied";

        if (utilisateur.getFonction() != fonction){
            utilisateur.setFonction(fonction);
            utilisateurRepository.save(utilisateur);
        }
        return "redirect:utilisateur";
    }//fin updateFonction

    /**
     * Méthode de mise à jour du grade de l'utilisateur courant
     * @param grade : grade à mettre à jour pour l'utilisateur courant
     * @param page : la page courante
     * @param size : la taille du tableau souhaitée par l'utilisateur
     * @param redirectAttributes : les attributs a rediriger.
     * @return redirection vers /utilisater => la vue espacepersonnel.html
     */
    @PostMapping("/updateGrade")
    public String updateGrade(@RequestParam("grade") Grade grade,
                              @RequestParam(name="page", defaultValue = DEFAULTPAGE) int page,
                              @RequestParam(name="size", defaultValue = DEFAULTSIZE) int size,
                              RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("page",page);
        redirectAttributes.addAttribute("size",size);

        Utilisateur utilisateur= UtilisateurUtil.getCurrentUtilisateur();
        if (utilisateur == null ) return "redirect:/accessDenied";

        if (utilisateur.getGrade() !=grade) {
            utilisateur.setGrade(grade);
            utilisateurRepository.save(utilisateur);
        }
        return "redirect:utilisateur";
    }// fin updateGrade
}
