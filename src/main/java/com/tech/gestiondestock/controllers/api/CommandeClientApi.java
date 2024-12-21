package com.tech.gestiondestock.controllers.api;

import static com.tech.gestiondestock.utils.Constants.COMMANDE_FOURNISSEUR_ENDPOINT;
import static com.tech.gestiondestock.utils.Constants.CREATE_COMMANDE_FOURNISSEUR_ENDPOINT;
import static com.tech.gestiondestock.utils.Constants.DELETE_COMMANDE_FOURNISSEUR_ENDPOINT;
import static com.tech.gestiondestock.utils.Constants.FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT;
import static com.tech.gestiondestock.utils.Constants.FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT;
import static com.tech.gestiondestock.utils.Constants.FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT;

import com.tech.gestiondestock.dto.CommandeClientDto;
import com.tech.gestiondestock.dto.CommandeFournisseurDto;
import com.tech.gestiondestock.dto.LigneCommandeClientDto;
import com.tech.gestiondestock.dto.LigneCommandeFournisseurDto;
import com.tech.gestiondestock.models.EtatCommande;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import static com.tech.gestiondestock.utils.Constants.APP_ROOT;

@Api("commandesclients")
public interface CommandeClientApi {



	  @PostMapping(APP_ROOT + "/commandesclients/create")
	  ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

	  @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{idCommande}/{etatCommande}")
	  ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

	  @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}")
	  ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande,
	      @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

	  @PatchMapping(APP_ROOT + "/commandesclients/update/client/{idCommande}/{idClient}")
	  ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

	  @PatchMapping(APP_ROOT + "/commandesclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}")
	  ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande") Integer idCommande,
	      @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

	  @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{idCommande}/{idLigneCommande}")
	  ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);

	  @GetMapping(APP_ROOT + "/commandesclients/{idCommandeClient}")
	  ResponseEntity<CommandeClientDto> findById(@PathVariable Integer idCommandeClient);

	  @GetMapping(APP_ROOT + "/commandesclients/filter/{codeCommandeClient}")
	  ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

	  @GetMapping(APP_ROOT + "/commandesclients/all")
	  ResponseEntity<List<CommandeClientDto>> findAll();

	  @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{idCommande}")
	  ResponseEntity<List<LigneCommandeClientDto>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("idCommande") Integer idCommande);

	  @DeleteMapping(APP_ROOT + "/commandesclients/delete/{idCommandeClient}")
	  ResponseEntity<Void> delete(@PathVariable("idCommandeClient") Integer id);
}

