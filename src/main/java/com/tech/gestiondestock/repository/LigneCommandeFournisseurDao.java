package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.LigneCommandeFournisseur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeFournisseurDao extends JpaRepository<LigneCommandeFournisseur, Integer> {
    List<LigneCommandeFournisseur> findAllByCommandeFournisseurId(Integer idCommande);

    List<LigneCommandeFournisseur> findAllByArticleId(Integer idCommande);

}
