package com.rochez.wikiTI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Langue :Les documentations doivent être écrites en plusieurs langues.
 * Cette entité est donc présente pour spécifier la langue de la documentation.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Langue {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @Column(columnDefinition="char(2)")
    private String code;

    @OneToMany(mappedBy = "langue")
    private List<Documentation> documentations;


    @Override
    public String toString() {
        return "Langue{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
