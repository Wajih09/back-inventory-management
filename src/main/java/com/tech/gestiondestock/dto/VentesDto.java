package com.tech.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

import com.tech.gestiondestock.models.Ventes;

@Data
@Builder
//v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
public class VentesDto {

    private Integer id;

    private String code;

    private Instant DateVente;

    private String commentaire;

    private Integer idEntreprise; //v13 min58

    //@OneToMany(mappedBy = "ventes")
    private List<LigneVenteDto> ligneVentes;

    public static VentesDto fromEntity(Ventes vente) {
        if (vente == null) {
          return null;
        }
        return VentesDto.builder()
            .id(vente.getId())
            .code(vente.getCode())
            .commentaire(vente.getCommentaire())
            .idEntreprise(vente.getIdEntreprise())
            .build();
      }
    
      public static Ventes toEntity(VentesDto dto) {
        if (dto == null) {
          return null;
        }
        Ventes ventes = new Ventes();
        ventes.setId(dto.getId());
        ventes.setCode(ventes.getCode());
        ventes.setCommentaire(dto.getCommentaire());
        ventes.setIdEntreprise(dto.getIdEntreprise());
        return ventes;
      }
}
