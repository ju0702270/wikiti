package com.rochez.wikiTI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.*;


/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Documentation :L’entité ´documentation´ est utilisée pour stocker les
 * différentes documentations. C’est un peu l’entité conductrice de la base de données.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Documentation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Size(min= 2, max = 255)
    private String titre;
    @Column(columnDefinition = "longtext")
    private String scope;


    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateCreation;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateModification;
    private boolean certified;
    @Min(value = 0)
    private double version;

    @ManyToOne
    @JsonIgnore
    private Langue langue;
    @ManyToOne
    @JsonIgnore
    private Type type;
    @ManyToOne
    @JsonIgnore
    private DegreSecurite degreSecurite;
    @ManyToOne
    @JsonIgnore
    private Utilisateur utilisateur_createur;
    @ManyToOne
    @JsonIgnore
    private Utilisateur utilisateur_modifieur;

    @OneToMany(mappedBy = "documentation", cascade = {CascadeType.ALL})
    private List<SectionContenu> sectionContenus= new ArrayList<>();


    @ManyToMany( fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="documentation_categorie",joinColumns = @JoinColumn(name="documentation_id") ,
            inverseJoinColumns = @JoinColumn(name="categorie_id"))
    private List<Categorie> categories= new ArrayList<>();

    public boolean isEmpty(){
        return ( this.id== null && this.titre== null && this.scope == null && this.dateCreation==null
                && this.dateModification == null
                && this.version== 0.0 && this.langue==null && this.type==null
                && this.degreSecurite==null && this.utilisateur_createur==null
                && this.utilisateur_modifieur==null && this.sectionContenus.size()==0 && this.categories.size()==0);
    }

}
