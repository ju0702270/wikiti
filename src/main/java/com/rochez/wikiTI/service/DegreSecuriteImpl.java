package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.DegreSecurite;
import com.rochez.wikiTI.repository.DegreSecuriteRepository;
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
public class DegreSecuriteImpl implements DegreSecuriteService{
    @Autowired
    DegreSecuriteRepository degreSecuriteRepository;
    @Override
    public DegreSecurite getDegreSecurite(Long id) {
        return degreSecuriteRepository.findById(id).get();
    }

    @Override
    public List<DegreSecurite> getAllDegreSecurite() {
        return degreSecuriteRepository.findAll();
    }
}
