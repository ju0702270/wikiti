package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Documentation;
import com.rochez.wikiTI.model.SectionContenu;
import com.rochez.wikiTI.repository.SectionContenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class Implémentation du service
 */
@Service
public class SectionContenuServiceImpl implements SectionContenuService{
    @Autowired
    SectionContenuRepository sectionContenuRepository;

    @Override
    public List<SectionContenu> getAllSectionContenu() {
        return sectionContenuRepository.findByOrderByOrdreAsc();
    }

    @Override
    public SectionContenu saveSectionContenu(SectionContenu sectionContenu) {
        return sectionContenuRepository.save(sectionContenu);
    }

    @Override
    public SectionContenu getSectionContenu(SectionContenu sectionContenu) {
        Optional<SectionContenu> sc = sectionContenuRepository.findById(sectionContenu.getId());
        return (sc.isEmpty())? null: sc.get();
    }

    @Override
    public boolean deleteSectionContenu(SectionContenu sectionContenu) {
        sectionContenuRepository.delete(sectionContenu);
        return sectionContenuRepository.findById(sectionContenu.getId()).isEmpty();
    }

    @Override
    public List<SectionContenu> getAllSectionContenuByDocumentation(Documentation documentation) {
        return sectionContenuRepository.findAllByDocumentation(documentation.getId());
    }
}
