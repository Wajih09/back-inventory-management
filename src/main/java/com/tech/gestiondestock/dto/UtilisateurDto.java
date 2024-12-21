package com.tech.gestiondestock.dto;

import com.tech.gestiondestock.models.Utilisateur;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
//v6 pour faire une copy (dto) contient seulement des champs spécéfiques qu'on veut l'exposer à une APi externe (angular ou autres)
public class UtilisateurDto {

    private Integer id;

    @NotBlank(message = "Nom du client non vide")
    private String nom;

    @NotBlank
    private String prenom;

    private Instant dateDeNaissance;

    private String motDePasse;

    @Embedded // v5 cad champ composé
    private AdresseDto adresse; //v5 puisque c composé de 3 sous-adresse et va se répété dans plusieurs entitées donc on va créer une classe adresse

    private String email;

    private String photo;

    private EntrepriseDto entreprise;

    private List<RolesDto> roles;

    //private Integer idEntreprise; //v13 min58

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if (utilisateur == null){
            return null;
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .email(utilisateur.getEmail())
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDto::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto dto) {
        if (dto == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(dto.getId());
        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setMotDePasse(dto.getMotDePasse());
        utilisateur.setDateDeNaissance(dto.getDateDeNaissance());
        utilisateur.setAdresse(AdresseDto.toEntity(dto.getAdresse()));
        utilisateur.setPhoto(dto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(dto.getEntreprise()));

        return utilisateur;
    }
}
