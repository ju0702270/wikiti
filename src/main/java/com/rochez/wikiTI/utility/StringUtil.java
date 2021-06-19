package com.rochez.wikiTI.utility;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.*;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Utilitaire pour les chaine de caractères
 */
public class StringUtil {
    public static Locale locale = new Locale( ""+LocaleContextHolder.getLocale());
    public static ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);

    /**
     * Méthode de récupération d'une chaine de caractère en i18n selon la langue courante
     * @param message : la clé de la chaine de caractère se trouvant dans les fichiers .properties spécifique au i18n
     * @return
     */
    public static String bundle(String message){
        return ResourceBundle.getBundle("messages",LocaleContextHolder.getLocale() ).getString(message);
    }

    /**
     * Méthode de récupération d'une chaine de caractère dans une liste de chaine de caractère
     * @param list
     * @return
     */
    public static String random(List<String> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }




}
