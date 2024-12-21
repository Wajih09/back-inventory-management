package com.tech.gestiondestock.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangerMotDePasseUtilisateurDto {

  private Integer id;

  private String motDePasse;

  private String confirmMotDePasse;

}
