package com.tech.gestiondestock.validator;

import com.tech.gestiondestock.dto.UtilisateurDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto){
        List<String> errors = new ArrayList<>();
        if(utilisateurDto==null){
            errors.add("Veuillez renseigner le nom de l'utilisateur");
            errors.add("Veuillez renseigner la prénom de l'utilisateur");
            errors.add("Veuillez renseigner la date de naissance de l'utilisateur");
            errors.add("Veuillez renseigner la mot de passe de l'utilisateur");
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
            errors.add("Veuillez renseigner l'email de l'utilisateur");
            return errors;
        }
        if(!StringUtils.hasLength(utilisateurDto.getNom())){
            errors.add("Veuillez renseigner le nom de l'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getPrenom())){
            errors.add("Veuillez renseigner la prénom de l'utilisateur");
        }
        if(utilisateurDto.getDateDeNaissance()==null) { // pas de StringUtils car ce champ n'est pas un String
            errors.add("Veuillez renseigner la date de naissance de l'utilisateur");
        }
        if(!StringUtils.hasLength(utilisateurDto.getMotDePasse())){
            errors.add("Veuillez renseigner la mot de passe de l'utilisateur");
        }
        if(utilisateurDto.getAdresse()==null) { // pas de StringUtils car ce champ n'est pas un String
            errors.add("Veuillez renseigner l'adresse de l'utilisateur");
        }
        else {
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())){ //v10 min4 adresse imbriquée
                errors.add("Le champs 'Adresse1' est obligatoire ");
            }
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getVille())){ //v10 min4 adresse imbriquée
                errors.add("Le champs 'Ville' est obligatoire ");
            }
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale())){ //v10 min4 adresse imbriquée
                errors.add("Le champs 'CodePostale' est obligatoire ");
            }
            if(!StringUtils.hasLength(utilisateurDto.getAdresse().getPays())){ //v10 min4 adresse imbriquée
                errors.add("Le champs 'Pays' est obligatoire ");
            }
        }
        if(!StringUtils.hasLength(utilisateurDto.getEmail())){
            errors.add("Veuillez renseigner l'email de l'utilisateur");
        }
        return errors;
    }
}
