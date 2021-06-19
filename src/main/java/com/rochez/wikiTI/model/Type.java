package com.rochez.wikiTI.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Type : Il existe différents types de documentation, ces types de
 * documentation sont repris dans cette entité.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Type  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;

    @OneToMany(mappedBy = "type")
    private List<Documentation> documentations;

    @Override
    public String toString() {
        return this.libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(id, type.id) && Objects.equals(libelle, type.libelle) && Objects.equals(description, type.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, description);
    }


}
