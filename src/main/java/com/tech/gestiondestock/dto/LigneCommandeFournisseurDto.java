package com.tech.gestiondestock.dto;

import com.tech.gestiondestock.models.Article;
import com.tech.gestiondestock.models.CommandeFournisseur;
import com.tech.gestiondestock.models.LigneCommandeFournisseur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommandeFournisseurDto {

    private Integer id;

    private Article article;

    private CommandeFournisseur commandeFournisseur;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    public static LigneCommandeFournisseurDto fromEntity(LigneCommandeFournisseur ligneCommandeFournisseur){

        if(ligneCommandeFournisseur == null) {
            return null;
        }
        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                .article(ligneCommandeFournisseur.getArticle())
                .build();
    }

    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){

        if(ligneCommandeFournisseurDto == null){
            return null;

        }
        LigneCommandeFournisseur LigneCommandeFournisseur = new LigneCommandeFournisseur();
        LigneCommandeFournisseur.setId(LigneCommandeFournisseur.getId());
        LigneCommandeFournisseur.setArticle(LigneCommandeFournisseur.getArticle());
        return LigneCommandeFournisseur;
    }
}
