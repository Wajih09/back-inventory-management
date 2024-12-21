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
public class LigneCommandeClientDto {

    private Integer id;

    private Article article;

    @JsonIgnore
    private CommandeClient commandeClient;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient){

        if(ligneCommandeClient == null) {
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

        }
        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDto.getId());
        ligneCommandeClient.setArticle(ligneCommandeClientDto.getArticle());
        ligneCommandeClient.setCommandeClient(ligneCommandeClientDto.getCommandeClient());
        return ligneCommandeClient;
    }
}
