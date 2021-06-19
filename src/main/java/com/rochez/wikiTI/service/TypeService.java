package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Categorie;
import com.rochez.wikiTI.model.Type;

import java.util.List;

public interface TypeService {
    Type getType(Long id);
    List<Type> getAllType();
}
