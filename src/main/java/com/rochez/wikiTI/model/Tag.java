package com.rochez.wikiTI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Tag(String libelle) {
        this.libelle = libelle;
    }

    private String libelle;

    /*@ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<SectionContenu> sectionContenus;*/



    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return o.toString().equals(tag.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }
}
