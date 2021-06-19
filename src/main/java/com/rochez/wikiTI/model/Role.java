package com.rochez.wikiTI.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;


/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Role :L’entité ´role´ est utilisée pour déterminer si un utilisateur a
 * un rôle tel qu’administrateur ou autre. Selon son rôle l’utilisateur aura des droits différents.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    @OneToMany(mappedBy = "role") //relié à la classe Utilisateur par un attribut role
    private List<Utilisateur> utilisateurs;

    @Override
    public String toString() {
        return this.libelle;
    }
}
