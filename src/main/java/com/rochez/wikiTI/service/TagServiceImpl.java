package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Tag;
import com.rochez.wikiTI.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;
    @Override
    public Tag getTag(Tag tag) {
        return tagRepository.findById(tag.getId()).get();
    }

    @Override
    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getAllTagByLibelle(String libelle) {
        return tagRepository.findByLibelle(libelle);
    }

    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
