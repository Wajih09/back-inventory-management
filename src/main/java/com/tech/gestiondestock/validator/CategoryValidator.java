package com.tech.gestiondestock.validator;

import com.tech.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {
    public static List<String> validate(CategoryDto categoryDto){
        List<String> errors = new ArrayList<>();
        if(categoryDto==null){
            errors.add("Veuillez renseigner le code de la catégorie");
            errors.add("Veuillez renseigner la désignation de l'article");
            return errors;
        }
        if(!StringUtils.hasLength(categoryDto.getCode())){
            errors.add("Veuillez renseigner le code de la catégorie");
        }
        if(!StringUtils.hasLength(categoryDto.getDesignation())){
            errors.add("Veuillez renseigner la désignation de l'article");
        }
        return errors;
    }
}
