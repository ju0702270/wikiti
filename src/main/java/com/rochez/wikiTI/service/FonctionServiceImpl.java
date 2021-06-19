package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Fonction;
import com.rochez.wikiTI.repository.FonctionRepository;
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
public class FonctionServiceImpl implements FonctionService{
    @Autowired
    FonctionRepository fonctionRepository;

    @Override
    public Fonction getFonction(Long id) {
        return fonctionRepository.findById(id).get();
    }

    @Override
    public List<Fonction> getAllFonction() {
        return fonctionRepository.findAll();
    }
}
