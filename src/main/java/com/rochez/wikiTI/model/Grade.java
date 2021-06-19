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
 * @Description : Classe Model Grade : L’entité ´grade´ est utilisée pour déterminer le grade d’un utilisateur.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Grade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String min;
    private String full;
    @Column(columnDefinition="char(3)")
    private String administratif;
    @Column(columnDefinition="char(3)")
    private String nato;

    @OneToMany(mappedBy = "grade")
    private List<Utilisateur> utilisateurs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(id, grade.id) && Objects.equals(min, grade.min) && Objects.equals(full, grade.full) && Objects.equals(administratif, grade.administratif) && Objects.equals(nato, grade.nato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, min, full, administratif, nato);
    }

    @Override
    public String toString() {
        return this.administratif;
    }
}
