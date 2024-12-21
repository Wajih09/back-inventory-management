package com.tech.gestiondestock.controllers.api;

import com.tech.gestiondestock.dto.CategoryDto;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.tech.gestiondestock.utils.Constants.APP_ROOT;

//@Tag(name = APP_ROOT + "/categories") //v17
@Api("categories")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "/category/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Enregistrer une category", description = "Cette methode permet d'enregistrer ou modifier une category", responses = { //tableau de plusieurs reponses
            @ApiResponse(responseCode = "200", description = "L'objet category cree / modifiee"),
            @ApiResponse(responseCode = "400", description = "L'objet category n'est pas valide")
    })
    CategoryDto save(@RequestBody CategoryDto categoryDto);

    @GetMapping(value = APP_ROOT + "/category/id/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher une category par ID", description = "Cette methode permet de rechercher une category par son ID", responses = {
            @ApiResponse(responseCode = "200", description = "La category a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune category trouve dans la BDD avec l'ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer id);

    @GetMapping(value = APP_ROOT + "/category/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Rechercher une category par code", description = "Cette methode permet de rechercher une category par son CODE", responses = {
            @ApiResponse(responseCode = "200", description = "La category a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune category trouve dans la BDD avec le code fourni")
    })
    CategoryDto findByCode(@PathVariable("code") String code);

    @GetMapping(value = APP_ROOT + "/category/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Renvoie la liste des categories", description = "Cette methode permet de rechercher et renvoyer la liste des categories existants dans la BDD", responses = {
            @ApiResponse(responseCode = "200", description = "La liste des categories / Une liste vide") //il ya pas de 404 car pas de validator pour cette liste
    })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/category/delete/{id}")
    @Operation(summary = "Supprimer une category", description = "Cette methode permet de supprimer une category par son ID"/*, responses = {
            @ApiResponse(responseCode = "200", description = "La category a ete supprime de la BDD")
    }v16 min52*/)
    void delete(@PathVariable("id") Integer id);
}
