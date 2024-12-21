package com.tech.gestiondestock.validator;

import com.tech.gestiondestock.dto.ClientDto;
import com.tech.gestiondestock.dto.FournisseurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto fournisseurDto){
        List<String> errors = new ArrayList<>();
        if(fournisseurDto==null){
            errors.add("Veuillez renseigner le nom du fournisseur");
            errors.add("Veuillez renseigner le prénom du fournisseur");
            errors.add("Veuillez renseigner l'email du fournisseur");
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur");
            return errors;
        }
        if(!StringUtils.hasLength(fournisseurDto.getNom())){
            errors.add("Veuillez renseigner le nom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getPrenom())){
            errors.add("Veuillez renseigner le prénom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getEmail())){
            errors.add("Veuillez renseigner le prénom du fournisseur");
        }
        if(!StringUtils.hasLength(fournisseurDto.getNumTel())){
            errors.add("Veuillez renseigner le numéro de téléphone du fournisseur");
        }
        return errors;
    }
}
