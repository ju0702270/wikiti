package com.rochez.wikiTI;

import com.rochez.wikiTI.model.Fonction;
import com.rochez.wikiTI.model.Grade;
import com.rochez.wikiTI.model.Role;
import com.rochez.wikiTI.model.Utilisateur;
import com.rochez.wikiTI.repository.UtilisateurRepository;
import com.rochez.wikiTI.service.*;
import groovy.transform.AutoImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author : Rochez Justin
 * @Creation : 25/05/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Main de l'application wiki-ti
 */
@SpringBootApplication
public class WikiTiApplication{

	public static void main(String[] args) {
		SpringApplication.run(WikiTiApplication.class, args);
	}


}
