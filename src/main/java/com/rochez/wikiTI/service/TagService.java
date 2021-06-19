package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Tag;

import java.util.List;

public interface TagService {
    Tag getTag(Tag tag);
    List<Tag> getAllTag();
    List<Tag> getAllTagByLibelle(String libelle);
    Tag getTag(Long id);
    Tag saveTag(Tag tag);
}
