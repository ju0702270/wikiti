package com.rochez.wikiTI.utility;

import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Utilitaire pour les utilisateurs
 */
public class UtilisateurUtil {


    /**
     * Méthode de récupération de l'utilisateur authentifié courant
     * @return Utilisateur ou null si il n'y à pas d'utilisateur authentifié
     */
    public static Utilisateur getCurrentUtilisateur(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals("anonymousUser")) {
            MyUserDetails userDetails= (MyUserDetails) authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }

    /**
     * Méthode de vérification de la validité d'une chaine supposant être un mot de passe
     * @param unencryptedPassword : le mot de passe non encrypté a verifier
     * @return boolean
     */
    public static boolean checkPasswordValidity(String unencryptedPassword){
        Pattern password = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,254}$");
        return (password.matcher(unencryptedPassword).find()) ;
    }

    /**
     * Méthode de hash d'un mot de passe
     * @param unenecryptedPassword le mot de passe non encrypté a hasher
     * @return le mot de passe encrypté
     */
    public static String encodePassword(String unenecryptedPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(unenecryptedPassword);
    }
}
