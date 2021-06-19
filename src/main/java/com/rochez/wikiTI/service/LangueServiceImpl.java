package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Langue;
import com.rochez.wikiTI.repository.LangueRepository;
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
public class LangueServiceImpl implements LangueService{
    @Autowired
    LangueRepository langueRepository;

    @Override
    public Langue getLangue(Long id) {
        return langueRepository.findById(id).get();
    }

    @Override
    public List<Langue> getAllLangue() {
        return langueRepository.findAll();
    }
}
