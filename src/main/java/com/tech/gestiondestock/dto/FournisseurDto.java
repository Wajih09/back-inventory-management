package com.tech.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.Fournisseur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)

public class FournisseurDto {

    private Integer id; // v7 necessaire pour la modification des entitées sauf AdresseDto pas de modif

    private String nom;

    private String prenom;

    private AdresseDto adresse;

    private String numTel;

    private String email;

    private String photo;

    private Integer idEntreprise; //v13 min58

    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity(Fournisseur fournisseur) {
        if (fournisseur == null) {
          return null;
        }
        return FournisseurDto.builder()
            .id(fournisseur.getId())
            .nom(fournisseur.getNom())
            .prenom(fournisseur.getPrenom())
            .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
            .photo(fournisseur.getPhoto())
            .email(fournisseur.getEmail())
            .numTel(fournisseur.getNumTel())
            .idEntreprise(fournisseur.getIdEntreprise())
            .build();
      }
    
    public static Fournisseur toEntity(FournisseurDto dto) {
        if (dto == null) {
          return null;
        }
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(dto.getId());
        fournisseur.setNom(dto.getNom());
        fournisseur.setPrenom(dto.getPrenom());
        fournisseur.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        fournisseur.setPhoto(dto.getPhoto());
        fournisseur.setEmail(dto.getEmail());
        fournisseur.setNumTel(dto.getNumTel());
        fournisseur.setIdEntreprise(dto.getIdEntreprise());
    
        return fournisseur;
      }
    

}
