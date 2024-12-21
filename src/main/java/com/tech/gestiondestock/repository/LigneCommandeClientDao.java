package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.LigneCommandeClient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeClientDao extends JpaRepository<LigneCommandeClient, Integer> {
    List<LigneCommandeClient> findAllByCommandeClientId(Integer id);

    List<LigneCommandeClient> findAllByArticleId(Integer id);
}
