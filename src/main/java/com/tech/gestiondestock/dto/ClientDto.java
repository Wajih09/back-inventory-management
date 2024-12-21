package com.tech.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.Client;

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

public class ClientDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private String nom;

    private String prenom;

    private AdresseDto adresse; //v5 puisque c composé de 3 sous-adresse et va se répété dans plusieurs entitées donc on va créer une classe adresse

    private String numTel;

    private String email;

    private String photo;

    @JsonIgnore //v7 min5 because we don't want to expose this field to external APIs / when exposing a list it will generate an infinite loop min 5:50 v7
    private List<CommandeClientDto> commandeClients;

    private Integer idEntreprise; //v13 min58

    public static ClientDto fromEntity(Client client){

        if(client == null) {
            //TODO Exception
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .photo(client.getPhoto())
                .email(client.getEmail())
                .numTel(client.getNumTel())
                .idEntreprise(client.getIdEntreprise())
                .build();
    }

    public static Client toEntity(ClientDto clientDto){

        if(clientDto == null){
            return null;
            //TODO Exception

        }
        Client client = new Client(); //v8
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setAdresse(AdresseDto.toEntity(clientDto.getAdresse()));
        client.setPhoto(clientDto.getPhoto());
        client.setEmail(clientDto.getEmail());
        client.setNumTel(clientDto.getNumTel());
        client.setIdEntreprise(clientDto.getIdEntreprise());
        return client;
    }
}
