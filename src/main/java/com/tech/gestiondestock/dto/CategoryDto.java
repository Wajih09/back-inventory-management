package com.tech.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private String code;

    private String designation;

    private Integer idEntreprise; //v13 min58

    @JsonIgnore //v7 min5 because we don't want to expose this field to external APIs / when exposing a list it will generate an infinite loop min 5:50 v7
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category){ //v7 faire un mapping de Category -> CategoryDto

        if(category == null) {
            //TODO Exception
            return null;
        }
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto){

        if(categoryDto == null){
            return null;
            //TODO Exception

        }
        Category category = new Category(); //v8
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        return category;
    }

}
