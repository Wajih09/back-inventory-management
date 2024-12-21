package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder    //v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
@EqualsAndHashCode(callSuper = true)
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity {

    @Column(name = "nom", unique = true)
    private String nom;

    @Column(name = "description")
    private String description;

    @Embedded // v5 cad champ composé
    private Adresse adresse; //v5 puisque c composé de 3 sous-adresse et va se répété dans plusieurs entitées donc on va créer une classe adresse

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

//    @Column(name = "identreprise")
//    private Integer idEntreprise;

    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs;


}
