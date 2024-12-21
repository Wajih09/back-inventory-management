package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@Table(name = "lignecommandefournisseur")
public class LigneCommandeFournisseur extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "idcommandefournisseur")
    private CommandeFournisseur commandeFournisseur;

    @JoinColumn(name = "quantite")
    private BigDecimal quantite;

    @Column(name = "prixunitaire")
    private BigDecimal prixUnitaire;

    @Column(name = "identreprise")
    private Integer idEntreprise;
}
