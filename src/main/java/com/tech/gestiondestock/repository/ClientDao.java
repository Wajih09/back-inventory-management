package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, Integer> {

    Optional<Client> findByNom(String nom);
}
