package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.DegreSecurite;

import java.util.List;

public interface DegreSecuriteService {
    DegreSecurite getDegreSecurite(Long id);
    List<DegreSecurite> getAllDegreSecurite();
}
