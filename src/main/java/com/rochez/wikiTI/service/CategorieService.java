package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Categorie;

import java.util.List;

public interface CategorieService {
    Categorie getCategorie(Long id);
    List<Categorie> getAllCategorie();
    Categorie saveCategorie(Categorie categorie);
}
