package com.tech.gestiondestock.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tech.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import com.tech.gestiondestock.dto.UtilisateurDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.exception.InvalidOperationException;
import com.tech.gestiondestock.models.Utilisateur;
import com.tech.gestiondestock.repository.UtilisateurDao;
import com.tech.gestiondestock.services.UtilisateurService;
import com.tech.gestiondestock.validator.UtilisateurValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurDao utilisateurDao;
	private final PasswordEncoder passwordEncoder;
	//private final ApplicationUserDetailsService applicationUserDetailsService;

	@Autowired
	public UtilisateurServiceImpl(UtilisateurDao utilisateurDao, PasswordEncoder passwordEncoder/*, ApplicationUserDetailsService applicationUserDetailsService*/) {
		this.utilisateurDao = utilisateurDao;
		this.passwordEncoder = passwordEncoder;
		//this.applicationUserDetailsService = applicationUserDetailsService;
	}

	@Override
	public UtilisateurDto save(UtilisateurDto dto) {
		List<String> errors = UtilisateurValidator.validate(dto);
		if (!errors.isEmpty()) {
			log.error("Utilisateur is not valid {}", dto);
			throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID,
					errors);
		}

		if (userAlreadyExists(dto.getEmail())) {
			throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja",
					ErrorCodes.UTILISATEUR_ALREADY_EXISTS,
					Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));
		}

		dto.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

		return UtilisateurDto.fromEntity(utilisateurDao.save(UtilisateurDto.toEntity(dto)));
	}

	private boolean userAlreadyExists(String email) {
		Optional<Utilisateur> user = utilisateurDao.findByEmail(email);
		return user.isPresent();
	}

	@Override
	public UtilisateurDto findById(Integer id) {
		if (id == null) {
			log.error("Utilisateur ID is null");
			return null;
		}
		return utilisateurDao.findById(id).map(UtilisateurDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException(
						"Aucun utilisateur avec l'ID = " + id + " n' ete trouve dans la BDD",
						ErrorCodes.UTILISATEUR_NOT_FOUND));
	}

	@Override
	public List<UtilisateurDto> findAll() {
		return utilisateurDao.findAll().stream().map(UtilisateurDto::fromEntity).collect(Collectors.toList());
	}

	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Utilisateur ID is null");
			return;
		}
		utilisateurDao.deleteById(id);
	}

	@Override
	public UtilisateurDto findByEmail(String email) {
		return utilisateurDao.findByEmail(email).map(UtilisateurDto::fromEntity)
				.orElseThrow(() -> new EntityNotFoundException(
						"Aucun utilisateur avec l'email = " + email + " n' ete trouve dans la BDD",
						ErrorCodes.UTILISATEUR_NOT_FOUND));
	}

	  @Override
	  public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
	    validate(dto);
	    Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(dto.getId());
	    if (utilisateurOptional.isEmpty()) {
	      log.warn("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId());
	      throw new EntityNotFoundException("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId(), ErrorCodes.UTILISATEUR_NOT_FOUND);
	    }

	    Utilisateur utilisateur = utilisateurOptional.get();
	    utilisateur.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

	    return UtilisateurDto.fromEntity(
	        utilisateurDao.save(utilisateur)
	    );
	  }

	private void validate(ChangerMotDePasseUtilisateurDto dto) {
		if (dto == null) {
			log.warn("Impossible de modifier le mot de passe avec un objet NULL");
			throw new InvalidOperationException(
					"Aucune information n'a ete fourni pour pouvoir changer le mot de passe",
					ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if (dto.getId() == null) {
			log.warn("Impossible de modifier le mot de passe avec un ID NULL");
			throw new InvalidOperationException("ID utilisateur null:: Impossible de modifier le mote de passe",
					ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if (!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())) {
			log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
			throw new InvalidOperationException(
					"Mot de passe utilisateur null:: Impossible de modifier le mote de passe",
					ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if (!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())) {
			log.warn("Impossible de modifier le mot de passe avec deux mots de passe different");
			throw new InvalidOperationException(
					"Mots de passe utilisateur non conformes:: Impossible de modifier le mote de passe",
					ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
	}
}
