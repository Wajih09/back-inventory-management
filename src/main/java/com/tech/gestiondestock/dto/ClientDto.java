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

    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String numTel;

    private String email;

    private String photo;

    @JsonIgnore
    private List<CommandeClientDto> commandeClients;

    private Integer idEntreprise;

    public static ClientDto fromEntity(Client client){

        if(client == null) {
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
        }
        Client client = new Client();
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
