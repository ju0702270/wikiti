package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Fonction;

import java.util.List;

public interface FonctionService {
    Fonction getFonction(Long id);
    List<Fonction> getAllFonction();
}
