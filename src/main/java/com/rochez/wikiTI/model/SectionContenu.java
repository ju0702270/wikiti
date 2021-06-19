package com.rochez.wikiTI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model SectionContenu : Chaque documentation possède du contenu, le contenu de la
 * documentation est divisé en sections ou chapitres.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SectionContenu {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Size(min= 2, max = 255)
    private String titre;
    @Column(columnDefinition="longtext")
    private String html;
    @NotNull
    private int ordre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentation_id")
    private Documentation documentation;

    /*@ManyToMany(cascade={CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name="section_contenu_tag",joinColumns = @JoinColumn(name="section_contenu_id") ,
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    private List<Tag> tags;*/




    /**
     * Méthode d'ajout d'un Tag vide à this.tags
     */
    /*public void addEmptyTag(){
        if (this.tags == null){
            this.tags = new ArrayList<>();
        }
        this.tags.add(new Tag());
    }
    public void addEmptyTag(int number){
        if (this.tags == null){
            this.tags = new ArrayList<>();
        }
        for (int i = 0; i < number; i++) {
            this.tags.add(new Tag());
        }

    }*/

    @Override
    public String toString() {
        return "SectionContenu{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", html='" + html + '\'' +
                ", ordre=" + ordre +
                '}';
    }
}
