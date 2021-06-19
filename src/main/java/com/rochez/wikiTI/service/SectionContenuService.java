package com.rochez.wikiTI.service;

import com.rochez.wikiTI.model.Documentation;
import com.rochez.wikiTI.model.SectionContenu;

import java.util.List;

public interface SectionContenuService {
    List<SectionContenu> getAllSectionContenu();
    SectionContenu saveSectionContenu(SectionContenu sectionContenu);
    SectionContenu getSectionContenu(SectionContenu sectionContenu);
    boolean deleteSectionContenu(SectionContenu sectionContenu);
    List<SectionContenu> getAllSectionContenuByDocumentation(Documentation documentation);

}
