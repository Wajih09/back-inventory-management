package com.tech.gestiondestock.handlers;
//v11 handlers package : gestionnaire d'exception globale = listner qui intercepte les exceptions levées dans l'application
//v11 l'objet qu'on va l'envoyer lorsqu'on catch une exception
import com.tech.gestiondestock.exception.ErrorCodes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {

    ErrorCodes errorCodes;
    String message;
    Date timestamp;
    Integer httpCode; // status à exposer expl resultat dans postman c pas comme les autres code error internes qu'on va builder dans la RestExcep
    List<String> errors = new ArrayList<>(); //initialisé ici
}
