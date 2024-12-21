package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrepriseDao extends JpaRepository<Entreprise, Integer> {

    Optional<Entreprise> findByNom(String nom);
}
