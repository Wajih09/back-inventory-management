package com.tech.gestiondestock.controllers;

import com.tech.gestiondestock.controllers.api.ArticleApi;
import com.tech.gestiondestock.dto.ArticleDto;
import com.tech.gestiondestock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi { //application fermée à la modification et ouverte à l'extension parceque on utilise les interfaces
    // et les implementations : si je veux changer l'implementation je dois créer suelement une nouvelle ArticleServiceImpl3
    // et je fais appel dans le controlleur à celle-ci dans @Qualifier

    @Autowired //dependency injection de frimework spring, il permet de creer automatiquement des instances, se fait quand on définit un champ referencié à une interface ou une classe (v15 Flickr)
    //3types : field injection : se fait au niveau des fields, getter injection equiv à la première et constructor injection : injecter les dependances au niveau de constructor
    //****v12 min53 içi au momment de la creation de l'objet articleService (field içi) dans mon controller => il va creer une classe de Bean ArticleServiceImpl1 et dedant il va implicitement injecter le reposetery par constructeur dans la ligne 25 de la classe ArticleServiceImpl1 (2 autowired dans cette transit)
    @Qualifier("ArticleServiceImpl1") //v12 min18 il faut préciser le bean(object implemente cette interface ArticleService) cad quel service il faut injecter pour creer une instance de ArticleService
    private ArticleService articleService; //v12 min50 types injections (n'oublie pas que ce type est une interface)****

    @Override
    public ArticleDto save(ArticleDto articleDto) { //v12 min54 bonne pratique de déléguer le business implementation(impl du code métier) au services cad : validation, verifier id null ou pas ...
        return articleService.save(articleDto);
    }

    @Override
    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
