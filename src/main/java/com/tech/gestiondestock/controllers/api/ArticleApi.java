package com.tech.gestiondestock.controllers.api;

import com.tech.gestiondestock.dto.ArticleDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/*import io.swagger.annotations.Api; //videos
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;*/
//import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.tech.gestiondestock.utils.Constants.APP_ROOT;

//@Tag(name = APP_ROOT + "/articles") //v16 min7 pour dire à swagger que ce controller est une api et je veux ajouter de la documentation à cette api, et on ajoute un nom à cette api entre()
//@Api(APP_ROOT + "/articles")
@Api("articles")
public interface ArticleApi {//interface permet d'avoir une visibilté mieux que la classe directement dans le controller

    @PostMapping(value = APP_ROOT /*static field importé de l'interface Constants dans utils*/ + "/article/create", consumes = MediaType.APPLICATION_JSON_VALUE/*on recoit en parametres un objet json*/, produces = MediaType.APPLICATION_JSON_VALUE/*on va produire un objet json*/)//pour enregistrer un article
    @ApiOperation(value = "Enregistrer un article ", notes = "Cette methode permet d'enregistrer ou modifier un article", response = ArticleDto.class)//v16 min8 il est preferable de documenter tous les methodes (methodes de controller = operations)
    @ApiResponses(value = { //videos
            @ApiResponse(code = 200, message = "L'objet article cree / modifiee"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
//    @Operation(summary = "Enregistrer un article ", description = "Cette methode permet d'enregistrer ou modifier un article", responses = { //tableau de plusieurs reponses
//            @ApiResponse(responseCode = "200", description = "L'objet article cree / modifiee"),
//            @ApiResponse(responseCode = "400", description = "L'objet article n'est pas valide")
//    })
    ArticleDto save(@RequestBody ArticleDto articleDto); //v12 min44 pour faire la désérialisation d'un text sous format Json (data) vers un objet ArticleDto

    @ApiOperation(value = "Rechercher un article par ID", notes = "Cette methode permet de chercher un article par son ID", response = ArticleDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
        @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
//    @Operation(summary = "Rechercher un article par ID", description = "Cette methode permet de rechercher un article par son ID", responses = {
//            @ApiResponse(responseCode = "200", description = "L'article a ete trouve dans la BDD"),
//            @ApiResponse(responseCode = "404", description = "Aucun article trouve dans la BDD avec l'ID fourni")
//    })
    @GetMapping(value = APP_ROOT + "/article/{idArticle}" /*path variable içi*/, produces = MediaType.APPLICATION_JSON_VALUE) // value ici peut etre parceque curl composé contenant variable APP_ROOT
    ArticleDto findById(@PathVariable("idArticle")/*préciser entre () car id qui suit n'a pas le meme nom que {idArticle}*/ Integer id);

    @ApiOperation(value = "Rechercher un article par CODE", notes = "Cette methode permet de chercher un article par son CODE", response =
    	      ArticleDto.class)
    	  @ApiResponses(value = {
    	      @ApiResponse(code = 200, message = "L'article a ete trouve dans la BDD"),
    	      @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    	  })
//    @Operation(summary = "Rechercher un article par code", description = "Cette methode permet de rechercher un article par son CODE", responses = {
//            @ApiResponse(responseCode = "200", description = "L'article a ete trouve dans la BDD"),
//            @ApiResponse(responseCode = "404", description = "Aucun article trouve dans la BDD avec le code fourni")
//    })
    @GetMapping(value = APP_ROOT + "/article/filter/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    ArticleDto findByCodeArticle(@Parameter(description = "v1 min24 Accepted values [article 1, article 2, article 3]") @PathVariable String codeArticle); // pas besoin de () après PathVariable car meme nom

    @ApiOperation(value = "Renvoi la liste des articles", notes = "Cette methode permet de chercher et renvoyer la liste des articles qui existent "
    	      + "dans la BDD", responseContainer = "List<ArticleDto>")
    	  @ApiResponses(value = {
    	      @ApiResponse(code = 200, message = "La liste des article / Une liste vide")
    	  })
//    @Operation(summary = "Renvoie la liste des articles", description = "Cette methode permet de rechercher et renvoyer la liste des articles existants dans la BDD", responses = {
//            @ApiResponse(responseCode = "200", description = "La liste des articles / Une liste vide") //il ya pas de 404 car pas de validator pour cette liste
//    })
    @GetMapping(value = APP_ROOT + "/article/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ArticleDto> findAll();

    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "L'article a ete supprime")
    })
//    @Operation(summary = "Supprimer un article ", description = "Cette methode permet de supprimer un article par son ID"/*, responses = {
//            @ApiResponse(responseCode = "200", description = "L'article a ete supprime de la BDD")
//    }v16 min52*/)
    @DeleteMapping(value = APP_ROOT + "/article/{idArticle}") //v12 min47 c pas obligatoire de faire /article/delete/{idArticle} car la methode est deja @DeleteMapping diffère à @PostMapping
    void delete(@PathVariable("idArticle") Integer idArticle);
}
