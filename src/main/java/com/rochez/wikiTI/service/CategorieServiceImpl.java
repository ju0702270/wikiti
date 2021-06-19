package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Categorie;
import com.rochez.wikiTI.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Implémentation du service
 */
@Service
public class CategorieServiceImpl implements  CategorieService {
    @Autowired
    CategorieRepository categorieRepository;

    @Override
    public Categorie getCategorie(Long id) {
        return categorieRepository.findById(id).get();
    }

    @Override
    public List<Categorie> getAllCategorie() {
        return categorieRepository.findAll();
    }

    @Override
    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }
}
