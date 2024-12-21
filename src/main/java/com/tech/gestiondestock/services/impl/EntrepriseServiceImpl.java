package com.tech.gestiondestock.services.impl;

import com.tech.gestiondestock.dto.EntrepriseDto;
import com.tech.gestiondestock.dto.RolesDto;
import com.tech.gestiondestock.dto.UtilisateurDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.repository.EntrepriseDao;
import com.tech.gestiondestock.repository.RolesDao;
import com.tech.gestiondestock.services.EntrepriseService;
import com.tech.gestiondestock.services.UtilisateurService;
import com.tech.gestiondestock.validator.EntrepriseValidator;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

  private EntrepriseDao entrepriseDao;
  private UtilisateurService utilisateurService;
  private RolesDao rolesDao;

  @Autowired
  public EntrepriseServiceImpl(EntrepriseDao entrepriseDao, UtilisateurService utilisateurService,
      RolesDao rolesDao) {
    this.entrepriseDao = entrepriseDao;
    this.utilisateurService = utilisateurService;
    this.rolesDao = rolesDao;
  }

  @Override
  public EntrepriseDto save(EntrepriseDto dto) {
    List<String> errors = EntrepriseValidator.validate(dto);
    if (!errors.isEmpty()) {
      log.error("Entreprise is not valid {}", dto);
      throw new InvalidEntityException("L'entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
    }
    EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(
        entrepriseDao.save(EntrepriseDto.toEntity(dto))
    );

    UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);
    UtilisateurDto savedUser = utilisateurService.save(utilisateur);

    RolesDto rolesDto = RolesDto.builder()
        .roleNom("ADMIN")
        .utilisateur(savedUser)
        .build();

    rolesDao.save(RolesDto.toEntity(rolesDto));

    return  savedEntreprise;
  }

  private UtilisateurDto fromEntreprise(EntrepriseDto dto) {
    return UtilisateurDto.builder()
        .adresse(dto.getAdresse())
        .nom(dto.getNom())
        .prenom(dto.getCodeFiscal())
        .email(dto.getEmail())
        .motDePasse(generateRandomPassword())
        .entreprise(dto)
        .dateDeNaissance(Instant.now())
        .photo(dto.getPhoto())
        .build();
  }

  private String generateRandomPassword() {
    return "ini/iALpAssw0rd";
  }

  @Override
  public EntrepriseDto findById(Integer id) {
    if (id == null) {
      log.error("Entreprise ID is null");
      return null;
    }
    return entrepriseDao.findById(id)
        .map(EntrepriseDto::fromEntity)
        .orElseThrow(() -> new EntityNotFoundException(
            "Aucune entreprise avec l'ID = " + id + " n' ete trouve dans la BDD",
            ErrorCodes.ENTREPRISE_NOT_FOUND)
        );
  }

  @Override
  public List<EntrepriseDto> findAll() {
    return entrepriseDao.findAll().stream()
        .map(EntrepriseDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Integer id) {
    if (id == null) {
      log.error("Entreprise ID is null");
      return;
    }
    entrepriseDao.deleteById(id);
  }
}

