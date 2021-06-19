package com.rochez.wikiTI.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.Doc;
import java.util.List;
import java.util.Objects;

/**
 * @Author : Rochez Justin
 * @Creation : 01/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model DegreSecurité: L’entité ´degre_securite´ représente le degré d’habilitation de
 * sécurité d’un utilisateur, ´degre_securite´ représente aussi le degré d’habilitation qu’il faut pour
 * accéder à la lecture d’une documentation.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DegreSecurite {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;

    @OneToMany(mappedBy = "degreSecurite")
    private List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "degreSecurite")
    private List<Documentation> documentations;

    public DegreSecurite(Long id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DegreSecurite that = (DegreSecurite) o;
        return Objects.equals(id, that.id) && Objects.equals(libelle, that.libelle) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, description);
    }
}
