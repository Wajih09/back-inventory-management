package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder //v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
@EqualsAndHashCode(callSuper = true)
@Table(name = "roles")
public class Roles extends AbstractEntity {

    @Column(name = "rolenom")
    private String roleNom;

    @Column(name = "identreprise")
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "utilisateur")
    private Utilisateur utilisateur;
}
