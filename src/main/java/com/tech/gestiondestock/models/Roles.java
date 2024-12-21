package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
