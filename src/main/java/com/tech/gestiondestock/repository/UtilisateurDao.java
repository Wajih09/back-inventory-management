package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer > {

    Optional<Utilisateur> findByNom(String nom);
    @Query(value = "select u from Utilisateur u where u.email = :email")
    Optional<Utilisateur> findByEmail(@Param("email") String email);


}
