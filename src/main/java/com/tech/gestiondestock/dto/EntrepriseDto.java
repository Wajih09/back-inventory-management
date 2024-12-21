package com.tech.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.Entreprise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EntrepriseDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private String nom;

    private String description;

    private AdresseDto adresse; //v5 puisque c composé de 3 sous-adresse et va se répété dans plusieurs entitées donc on va créer une classe adresse

    private String codeFiscal;

    private String photo;

    private String email;

    private String numTel;

    private String siteWeb;

    //private Integer idEntreprise; //v13 min58

    @JsonIgnore
    List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise){

        if(entreprise == null) {
            //TODO Exception
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .siteWeb(entreprise.getSiteWeb())
                .build();
    }

    public static Entreprise toEntity(EntrepriseDto entrepriseDto){

        if(entrepriseDto == null){
            return null;
            //TODO Exception

        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entreprise.getId());
        entreprise.setNom(entrepriseDto.getNom());
        entreprise.setDescription(entrepriseDto.getDescription());
        entreprise.setAdresse(AdresseDto.toEntity(entrepriseDto.getAdresse()));
        entreprise.setCodeFiscal(entrepriseDto.getCodeFiscal());
        entreprise.setPhoto(entrepriseDto.getPhoto());
        entreprise.setEmail(entrepriseDto.getEmail());
        entreprise.setNumTel(entrepriseDto.getNumTel());
        entreprise.setSiteWeb(entrepriseDto.getSiteWeb());
        return entreprise;
    }


}
