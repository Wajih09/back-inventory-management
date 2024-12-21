package com.tech.gestiondestock.controllers.api;

import com.tech.gestiondestock.dto.ArticleDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.tech.gestiondestock.utils.Constants.APP_ROOT;

@Api("articles")
public interface ArticleApi {

    @PostMapping(value = APP_ROOT + "/article/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un article ", notes = "Cette methode permet d'enregistrer ou modifier un article", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet article cree / modifiee"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })

    ArticleDto save(@RequestBody ArticleDto articleDto);

    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet de chercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
        @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })

    @GetMapping(value = APP_ROOT + "/article/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE) 
    ArticleDto findById(@PathVariable("idArticle") Integer id);

    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet de chercher un article par son CODE", response =
    	      ArticleDto.class)
    	  @ApiResponses(value = {
    	      @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
    	      @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    	  })

    @GetMapping(value = APP_ROOT + "/article/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findByCodeArticle(@Parameter(description = "v1 min24 Accepted values [article 1, article 2, article 3]") @PathVariable String codeArticle); 

    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existent "
    	      + "dans la BDD", responseContainer = "List<ArticleDto>")
    	  @ApiResponses(value = {
    	      @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    	  })

    @GetMapping(value = APP_ROOT + "/article/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAll();

    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "L'article a ete supprime")
    })

    @DeleteMapping(value = APP_ROOT + "/article/{idArticle}")
    void delete(@PathVariable("idArticle") Integer idArticle);
}
