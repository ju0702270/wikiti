package com.rochez.wikiTI.service;


import com.rochez.wikiTI.model.Langue;

import java.util.List;

public interface LangueService {
    Langue getLangue(Long id);
    List<Langue> getAllLangue();
}
