package com.rochez.wikiTI.controllers.documentation;

import com.rochez.wikiTI.model.Documentation;
import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.service.DocumentationService;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Author : Rochez Justin
 * @Creation : 13/06/2021
 * @Modification : 13/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour l'affichage de documentation
 */
@Controller
@RequestMapping("/show")
public class DocumentationShowController {
    @Autowired
    DocumentationService documentationService;

    /**
     * Méthode Principal du controller DocumentationShowController
     * Cette méthode à pour but de récuperer l'id de la documentation passée dans l'url, de vérifier
     * si la documentation n'est pas en version Draft (0.0) et de vérifier si l'utilisateur à le degré de sécurité
     * suffisant pour accès à la documentation
     * @param idDoc : l'id de la documentation à afficher
     * @param model
     * @return String la vue documentation_show qui est l'affichage de la documentation
     */
    @GetMapping("/{idDoc}")
    public String getDocumentationShow(@PathVariable("idDoc") Long idDoc, Model model){
        try{
            Utilisateur utilisateur = UtilisateurUtil.getCurrentUtilisateur();
            Documentation documentation = documentationService.getDocumentation(idDoc);

            //verification si la documentation à une version 0.0 c'est un draft alors seul l'utilisateur qui est en train de l'écrire peut la voir
            if (documentation.getVersion() == 0.0 && !documentation.getUtilisateur_createur().equals(utilisateur))
                return "documentation_draft";
            model.addAttribute("documentation", documentation);
            //Si l'utilisateur est authentifié et que son degré de sécurité est suffisant alors on affiche la page
            //OU si l'utilisateur n'est pas authentifié et que le degré de sécurité de la documentation est au minimum
            if ( (utilisateur != null && utilisateur.getDegreSecurite().getId() >= documentation.getDegreSecurite().getId()) ||
                    utilisateur == null && documentation.getDegreSecurite().getId() == 1){
                return "documentation_show";
            }else{
                return "redirect:/accessDenied";
            }
        }catch (Exception e){
            return "redirect:/error";
        }
    }//fin  getDocumentationShow

    /**
     * Méthode qui récuper l'id d'une documentation à certifier
     * cette méthode ne peut etre effective que si c'est un ADMIN qui la lance
     * @param idDoc : l'id de la documentation à afficher
     * @return String la vue documentation_show qui est l'affichage de la documentation
     */
    @GetMapping("/{idDoc}/certified")
    public String certified(@PathVariable("idDoc") Long idDoc){
        try{
            Documentation documentation = documentationService.getDocumentation(idDoc);
            Utilisateur currentUser= UtilisateurUtil.getCurrentUtilisateur();
            if(currentUser != null && currentUser.getRole().getLibelle().equals("ADMIN")){
                documentation.setCertified(true);
                documentationService.saveDocumentation(currentUser.getDegreSecurite(),documentation);
            }
            return "redirect:/show/"+idDoc;
        }catch (Exception e){
            return "redirect:/error";
        }
    }//fin certified
}
