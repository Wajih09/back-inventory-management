package com.tech.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tech.gestiondestock.models.Roles;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RolesDto {

    private Integer id;

    private String roleNom;

    @JsonIgnore
    private UtilisateurDto utilisateur;

    private Integer idEntreprise;

    public static RolesDto fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return RolesDto.builder()
                .id(roles.getId())
                .roleNom(roles.getRoleNom())
                .build();
    }

    public static Roles toEntity(RolesDto dto) {
        if (dto == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setId(dto.getId());
        roles.setRoleNom(dto.getRoleNom());
        roles.setUtilisateur(UtilisateurDto.toEntity(dto.getUtilisateur()));
        return roles;
    }
}
