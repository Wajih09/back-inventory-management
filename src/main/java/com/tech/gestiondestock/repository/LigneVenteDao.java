package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.LigneVente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneVenteDao extends JpaRepository<LigneVente, Integer> {
    List<LigneVente> findAllByArticleId(Integer idArticle);

    List<LigneVente> findAllByVenteId(Integer id);

}
