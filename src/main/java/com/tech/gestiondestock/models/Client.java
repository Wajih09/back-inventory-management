package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder //v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
@EqualsAndHashCode(callSuper = true)
@Table(name = "client")
public class Client extends AbstractEntity {

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Embedded // v5 cad champ composé mais c pas pour toute les classes comme id et dateCreation dans AbstractEntity
    private Adresse adresse; //v5 puisque c composé de 3 sous-adresse et va se répété dans plusieurs entitées donc on va créer une classe adresse

    @Column(name = "numtel")
    private String numTel;

    @Column(name = "email")
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "identreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "client")
    private List<CommandeClient> commandeClients;
}
