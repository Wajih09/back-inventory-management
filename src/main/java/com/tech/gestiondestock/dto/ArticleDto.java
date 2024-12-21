package com.tech.gestiondestock.dto;

import com.tech.gestiondestock.models.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private String codeArticle;

    private String designation;

    private BigDecimal prixUnitaireHt;

    private BigDecimal tauxTVA;

    private BigDecimal prixUnitaireTtc;

    private String photo;

    private CategoryDto category; //v6

    private Integer idEntreprise; //v13 min58

    public static ArticleDto fromEntity(Article article){

        if(article == null) {
            //TODO Exception
            return null;
        }
        return ArticleDto.builder()
        		.id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .photo(article.getPhoto())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .tauxTVA(article.getTauxTVA())
                .idEntreprise(article.getIdEntreprise())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .idEntreprise(article.getIdEntreprise()) //v13 min58 TODO for other dto
                .build();
    }

    public static Article toEntity(ArticleDto articleDto){

        if(articleDto == null){
            return null;
            //TODO Exception

        }
        Article article = new Article(); //v8
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setPhoto(articleDto.getPhoto());
        article.setTauxTVA(articleDto.getTauxTVA());
        article.setIdEntreprise(articleDto.getIdEntreprise());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
        return article;
    }
}
