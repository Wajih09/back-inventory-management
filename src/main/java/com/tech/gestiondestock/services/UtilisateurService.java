package com.tech.gestiondestock.services;

import java.util.List;

import com.tech.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.tech.gestiondestock.dto.UtilisateurDto;

public interface UtilisateurService {

	UtilisateurDto save(UtilisateurDto dto);

	UtilisateurDto findById(Integer id);

	List<UtilisateurDto> findAll();

	void delete(Integer id);

	UtilisateurDto findByEmail(String email);

	UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
