package com.rochez.wikiTI.security;


import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.utility.UtilisateurUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Controller pour la sécurité de l'application
 */
@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException
    {
        request.logout();
        return "redirect:/index";
    }

    @GetMapping("/accessDenied")
    public String accesDenied() throws ServletException
    {
        return "accessDenied";
    }

    @GetMapping("/error")
    public String error() throws ServletException
    {
        return "error";
    }



}
