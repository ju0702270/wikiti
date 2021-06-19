package com.rochez.wikiTI.controllers;

import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.service.UtilisateurService;
import com.rochez.wikiTI.utility.StringUtil;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;

/**
 * @Author : Rochez Justin
 * @Creation : 18/06/2021
 * @Modification : 18/06/2021
 * @Revision : 1.0
 * @Description : Classe controller pour le changements de mot de passe
 */
@Controller
public class ChangePassController {
    @Autowired
    UtilisateurService utilisateurService;

    /**
     * Méthode d'accès à la page login.html modifiée pour le changement de mot de passe
     * @param model
     * @return index.html
     */
    @GetMapping("/changePass")
    public String changePass(Model model)  {
        Utilisateur currentUser = UtilisateurUtil.getCurrentUtilisateur();
        if (currentUser != null){
            if (currentUser.isResetpass()) model.addAttribute("isResetPass", StringUtil.bundle("label.needChangePass"));
            model.addAttribute("currentUser",currentUser);
            model.addAttribute("changePass", true);
            return "login";
        }else{
            return "redirect:/index";
        }

    }//fin changePass

    /**
     * Méthode de changement de mot de passe, cette méthode vérifie la correspondance du username (mail), du mot de passe actuelle
     * , du nouveau mot de passe avec le mot de passe de confirmation
     * Cette méthode vérifie aussi si le nouveau mot de passe est différent de l'ancien, et s'il est valide (1 uppercase, 1 lowercase, 1 digit
     * ,1 spécial char)
     * @param model
     * @param username : l'email de l'utilisateur faisant office de username
     * @param oldPassword: le mot de passe actuelle
     * @param newPassword : le nouveau mot de passe
     * @param confirmPassword : une vérification du nouveau mot de passe
     * @return ResponseEntity contenant une hashmap avec les messages d'erreur ou de succès + un HttpStatus
     */
    @PostMapping("/effectiveChangePass")
    public ResponseEntity<HashMap<String, String>> effectiveChangePass(Model model,
                                      @RequestParam("username") String username,
                                      @RequestParam("oldPassword") String oldPassword,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword){
        HashMap<String, String> mapError = new HashMap<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Utilisateur utilisateur = UtilisateurUtil.getCurrentUtilisateur();

        if (utilisateur == null) return new ResponseEntity<HashMap<String, String>>(HttpStatus.FORBIDDEN);


        /* verification du username */
        if (!utilisateur.getMail().equals(username)){
            mapError.put("usernameError", StringUtil.bundle("error.mail_bad"));
            return new ResponseEntity<HashMap<String, String>>(mapError, HttpStatus.NOT_FOUND);
        }
        /* vérification de l'ancien password */
        if ( !passwordEncoder.matches(oldPassword,utilisateur.getPassword())) {
            mapError.put("oldPasswordError", StringUtil.bundle("error.passwordnotgood"));
            return new ResponseEntity<HashMap<String, String>>(mapError, HttpStatus.NOT_FOUND);
        }
        /* vérification de la nom correspondance des ancien et nouveau mot de passe  */
        if ( passwordEncoder.matches(oldPassword,passwordEncoder.encode(newPassword))) {
            mapError.put("sameOldAndNewPasswordError", StringUtil.bundle("error.passnew_issamethanolder"));
            return new ResponseEntity<HashMap<String, String>>(mapError, HttpStatus.NOT_FOUND);
        }
        if (!UtilisateurUtil.checkPasswordValidity(newPassword)){
            mapError.put("newPasswordErrorValidation", StringUtil.bundle("error.passwordValidation"));
            return new ResponseEntity<HashMap<String, String>>(mapError, HttpStatus.BAD_REQUEST);
        }
        /* vérication de la corrspondance du nouveau password avec le password de confirmation */
        if ( !newPassword.equals(confirmPassword) ){
            mapError.put("confirmPasswordError", StringUtil.bundle("label.successChangePass"));
            return new ResponseEntity<HashMap<String, String>>(mapError, HttpStatus.BAD_REQUEST);
        }
        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateur.setResetpass(false);
        utilisateurService.saveUtilisateur(utilisateur);


        HashMap<String, String> mapSucces = new HashMap<>();//message en cas de succes
        mapSucces.put("success", StringUtil.bundle("label.successChangePass"));
        return new ResponseEntity<HashMap<String, String>>(mapSucces, HttpStatus.OK);
    }



}
