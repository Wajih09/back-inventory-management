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
@Builder
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

    @Embedded 
    private Adresse adresse; 

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "utilisateur")
    private List<Roles> roles;
    
    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "identreprise")
    private Entreprise entreprise;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "utilisateur")
    @JsonIgnore
    private List<Roles> roles;



}
