package com.rochez.wikiTI.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rochez.wikiTI.utility.StringUtil;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description : Classe Model Utilisateur : L’entité ´utilisateur´ est utilisée pour stocker les données des
 * utilisateurs authentifiés.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Size(min = 7, max = 7)
    @Pattern(regexp = "^\\d*\\d$")//uniquement des chiffres
    @NotNull
    @Column(unique=true)
    private String matricule;

    @Size(min = 2)
    private String nom;
    @Size(min = 2)
    private String prenom;

    @Email
    @Column(unique=true)
    private String mail;



    //ToDo, utiliser un pattern pour la validation
    @JsonIgnore
    private String password;
    private boolean resetpass;

    @ManyToOne
    @JsonIgnore
    private Role role;
    @ManyToOne
    @JsonIgnore
    private Grade grade;
    @ManyToOne
    @JsonIgnore
    private Fonction fonction;
    @ManyToOne
    @JsonIgnore
    private DegreSecurite degreSecurite;

    @OneToMany(mappedBy = "utilisateur_createur")
    private List<Documentation> documentations_createur;

    @OneToMany(mappedBy = "utilisateur_modifieur")
    private List<Documentation> documentations_modifieur;

    public Utilisateur(String matricule, String nom, String prenom, String mail, boolean resetpass, Role role, Grade grade, Fonction fonction, DegreSecurite degreSecurite) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.resetpass = resetpass;
        this.role = role;
        this.grade = grade;
        this.fonction = fonction;
        this.degreSecurite = degreSecurite;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", matricule='" + matricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", mail='" + mail + '\'' +
                ", resetpass=" + resetpass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return resetpass == that.resetpass && id.equals(that.id) && matricule.equals(that.matricule) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && mail.equals(that.mail) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matricule, nom, prenom, mail, password, resetpass);
    }
}
