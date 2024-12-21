package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder //v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
@EqualsAndHashCode(callSuper = true)
@Table(name = "utilisateur")
public class Utilisateur extends AbstractEntity {

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "datedenaissance")
    private Instant dateDeNaissance;

    @Column(name = "motdepasse")
    private String motDePasse;

    @Embedded // v5 cad champ composé
    private Adresse adresse; //v5 puisque c composé de 3 sous-adresse et va se répété dans plusieurs entitées donc on va créer une classe adresse

    @Column(name = "email", unique = true) //min44 https://www.youtube.com/watch?v=xqhdRrFzLFY&list=PL41m5U3u3wwl5FoM2Y5gIu1Q-Wr5ascD_&index=1
    private String email;

    @Column(name = "photo")
    private String photo;

//    @Column(name = "identreprise")
//    private Integer idEntreprise;
//
//    @ManyToOne
//    @JoinColumn(name = "entreprise")
//    private Entreprise entreprise;
    
//
//    @OneToMany(mappedBy = "utilisateur")
//    private List<Roles> roles;
    
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "identreprise")
    private Entreprise entreprise;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utilisateur")
    @JsonIgnore
    private List<Roles> roles;



}
