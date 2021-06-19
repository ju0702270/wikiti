package com.rochez.wikiTI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Fonction :L’entité ´fonction´ est utilisée pour avoir la liste des fonctions
 * disponibles. Celle-ci peut être assez large surtout à la défense.
 */
@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Fonction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;

    @OneToMany(mappedBy = "fonction")
    private List<Utilisateur> utilisateurs;

    @Override
    public String toString() {
        return this.libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fonction fonction = (Fonction) o;
        return Objects.equals(id, fonction.id) && Objects.equals(libelle, fonction.libelle) && Objects.equals(description, fonction.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, description);
    }
}
