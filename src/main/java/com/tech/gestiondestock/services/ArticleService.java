package com.tech.gestiondestock.services;

import com.tech.gestiondestock.dto.ArticleDto;

import java.util.List;

public interface ArticleService { //v12 on utilise une interface pour définir un contrat de service comme : JPA (interface) est la specification et Hibernate est un frimework qui l'implemente

    ArticleDto save(ArticleDto articleDto);
    ArticleDto findById(Integer id);
    ArticleDto findByCodeArticle(String codeArticle);
    List<ArticleDto> findAll();
    void delete(Integer id);

}
