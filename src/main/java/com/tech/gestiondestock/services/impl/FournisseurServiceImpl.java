package com.tech.gestiondestock.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.gestiondestock.dto.FournisseurDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.exception.InvalidOperationException;
import com.tech.gestiondestock.models.CommandeClient;
import com.tech.gestiondestock.repository.CommandeFournisseurDao;
import com.tech.gestiondestock.repository.FournisseurDao;
import com.tech.gestiondestock.services.FournisseurService;
import com.tech.gestiondestock.validator.FournisseurValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurDao fournisseurRepository;
    private CommandeFournisseurDao commandeFournisseurDao;
  
    @Autowired
    public FournisseurServiceImpl(FournisseurDao fournisseurRepository,
        CommandeFournisseurDao commandeFournisseurDao) {
      this.fournisseurRepository = fournisseurRepository;
      this.commandeFournisseurDao = commandeFournisseurDao;
    }
  
    @Override
    public FournisseurDto save(FournisseurDto dto) {
      List<String> errors = FournisseurValidator.validate(dto);
      if (!errors.isEmpty()) {
        log.error("Fournisseur is not valid {}", dto);
        throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
      }
  
      return FournisseurDto.fromEntity(
          fournisseurRepository.save(
              FournisseurDto.toEntity(dto)
          )
      );
    }
  
    @Override
    public FournisseurDto findById(Integer id) {
      if (id == null) {
        log.error("Fournisseur ID is null");
        return null;
      }
      return fournisseurRepository.findById(id)
          .map(FournisseurDto::fromEntity)
          .orElseThrow(() -> new EntityNotFoundException(
              "Aucun fournisseur avec l'ID = " + id + " n' ete trouve dans la BDD",
              ErrorCodes.FOURNISSEUR_NOT_FOUND)
          );
    }
  
    @Override
    public List<FournisseurDto> findAll() {
      return fournisseurRepository.findAll().stream()
          .map(FournisseurDto::fromEntity)
          .collect(Collectors.toList());
    }
  
    @Override
    public void delete(Integer id) {
      if (id == null) {
        log.error("Fournisseur ID is null");
        return;
      }
      List<CommandeClient> commandeFournisseur = commandeFournisseurDao.findAllByFournisseurId(id);
      if (!commandeFournisseur.isEmpty()) {
        throw new InvalidOperationException("Impossible de supprimer un fournisseur qui a deja des commandes",
            ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
      }
      fournisseurRepository.deleteById(id);
    }
  }
  