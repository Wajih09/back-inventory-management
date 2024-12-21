package com.tech.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.Article;
import com.tech.gestiondestock.models.CommandeClient;
import com.tech.gestiondestock.models.LigneCommandeClient;

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
//v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
public class LigneCommandeClientDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private Article article;

    @JsonIgnore
    private CommandeClient commandeClient;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise; //v13 min58

    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){

        if(ligneCommandeClient == null) {
            //TODO Exception
            return null;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .article(ligneCommandeClient.getArticle())
                .commandeClient(ligneCommandeClient.getCommandeClient())
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto){

        if(ligneCommandeClientDto == null){
            return null;
            //TODO Exception

        }
        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setArticle(ligneCommandeClientDto.getArticle());
        ligneCommandeClient.setCommandeClient(ligneCommandeClientDto.getCommandeClient());
        return ligneCommandeClient;
    }
}
