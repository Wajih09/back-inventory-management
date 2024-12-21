package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity {

    @Column(name = "nom", unique = true)
    private String nom;

    @Column(name = "description")
    private String description;

    @Embedded
    private Adresse adresse;

    @Column(name = "codefiscal")
    private String codeFiscal;

    @Column(name = "photo")
    private String photo;

    @Column(name = "email")
    private String email;

    @Column(name = "numtel")
    private String numTel;

    @Column(name = "siteweb")
    private String siteWeb;

    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs;


}
