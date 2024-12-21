package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurDao extends JpaRepository<Fournisseur, Integer> {

    Optional<Fournisseur> findByNom(String nom);
}
