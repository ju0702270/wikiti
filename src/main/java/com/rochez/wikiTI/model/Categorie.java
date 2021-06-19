package com.rochez.wikiTI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Catégorie: La catégorie est utilisée pour catégoriser une
 * documentation. Sachant qu’une documentation peut avoir plusieurs catégories.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Categorie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="documentation_categorie",joinColumns = @JoinColumn(name="categorie_id") ,
            inverseJoinColumns = @JoinColumn(name="documentation_id"))
    @JsonIgnore
    private List<Documentation> documentations;


    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
