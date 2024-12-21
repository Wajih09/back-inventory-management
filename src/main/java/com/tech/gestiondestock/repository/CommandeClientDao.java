package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeClientDao extends JpaRepository<CommandeClient, Integer> {

    Optional<CommandeClient> findByCode(String code);
    List<CommandeClient> findAllByClientId(Integer id);
}
