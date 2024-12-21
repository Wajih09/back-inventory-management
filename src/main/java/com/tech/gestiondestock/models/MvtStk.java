package com.tech.gestiondestock.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder //v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
@EqualsAndHashCode(callSuper = true)
@Table(name = "mvtstk")
public class MvtStk extends AbstractEntity {

    @Column(name = "datemvt")
    private Instant dateMvt;
  
    @Column(name = "quantite")
    private BigDecimal quantite;
  
    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;
  
    @Column(name = "typemvt")
    @Enumerated(EnumType.STRING)
    private TypeMvtStk typeMvt;
  
    @Column(name = "sourcemvt")
    @Enumerated(EnumType.STRING)
    private SourceMvtStk sourceMvt;
  
    @Column(name = "identreprise")
    private Integer idEntreprise;
}
