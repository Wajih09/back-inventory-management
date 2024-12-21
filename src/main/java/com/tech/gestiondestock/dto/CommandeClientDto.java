package com.tech.gestiondestock.dto;

import com.tech.gestiondestock.models.EtatCommande;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.CommandeClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandeClientDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private ClientDto client;//v13 min10 ici dto par analogie au client dans commandeClient

    private Integer idEntreprise; //v13 min58

    //@JsonIgnore
    private List<LigneCommandeClientDto> lignesCommandeClients; //v13 min11

    public static CommandeClientDto fromEntity(CommandeClient commandeClient){

        if(commandeClient == null) {
            //TODO Exception
            return null;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient())) //v13
                .idEntreprise(commandeClient.getIdEntreprise())
                .build();
    }

    public static CommandeClient toEntity(CommandeClientDto commandeClientDto){

        if(commandeClientDto == null){
            return null;
            //TODO Exception

        }
        CommandeClient commandeClient = new CommandeClient(); //v8
        commandeClient.setId(commandeClientDto.getId());
        commandeClient.setCode(commandeClientDto.getCode());
        commandeClient.setClient(ClientDto.toEntity(commandeClientDto.getClient()));
        commandeClient.setDateCommande(commandeClientDto.getDateCommande());
        commandeClient.setEtatCommande(commandeClientDto.getEtatCommande());
        commandeClient.setIdEntreprise(commandeClientDto.getIdEntreprise());
        return commandeClient;
    }
    
    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
      }
}
