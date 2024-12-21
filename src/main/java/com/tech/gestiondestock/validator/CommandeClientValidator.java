package com.tech.gestiondestock.validator;

import com.tech.gestiondestock.dto.CommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {
    public static List<String> validate(CommandeClientDto commandeClientDto) {
        List<String> errors = new ArrayList<>();
        if(commandeClientDto==null){
            errors.add("Veuillez renseigner le code de la commande client");
            errors.add("Veuillez renseigner la date de commande client");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez sélectionner un client");
            return errors;
        }
        if(!StringUtils.hasLength(commandeClientDto.getCode())){
            errors.add("Veuillez renseigner le code de la commande client");
        }
        if(commandeClientDto.getDateCommande()==null){
            errors.add("Veuillez renseigner la date de commande client");
        }
        if (!StringUtils.hasLength(commandeClientDto.getEtatCommande().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
          }
          if (commandeClientDto.getClient() == null || commandeClientDto.getClient().getId() == null) {
            errors.add("Veuillez renseigner le client");
          }
        return errors;
    }
}
