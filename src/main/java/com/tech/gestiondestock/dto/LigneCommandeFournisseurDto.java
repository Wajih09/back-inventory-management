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
//v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)

public class LigneCommandeFournisseurDto {

    private Integer id;

    private Article article;

    private CommandeFournisseur commandeFournisseur; //v6

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise; //v13 min58

    public static LigneCommandeFournisseurDto fromEntity(LigneCommandeFournisseur ligneCommandeFournisseur){

        if(ligneCommandeFournisseur == null) {
            //TODO Exception
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
            //TODO Exception

        }
        LigneCommandeFournisseur LigneCommandeFournisseur = new LigneCommandeFournisseur(); //v8
        LigneCommandeFournisseur.setId(LigneCommandeFournisseur.getId());
        LigneCommandeFournisseur.setArticle(LigneCommandeFournisseur.getArticle());
        return LigneCommandeFournisseur;
    }
}
