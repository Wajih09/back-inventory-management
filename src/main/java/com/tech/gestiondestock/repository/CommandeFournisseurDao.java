package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.CommandeClient;
import com.tech.gestiondestock.models.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurDao extends JpaRepository<CommandeFournisseur, Integer> {

    Optional<CommandeFournisseur> findByCode(String code);
    List<CommandeClient> findAllByFournisseurId(Integer id);

}
