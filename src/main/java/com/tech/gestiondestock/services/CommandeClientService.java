package com.tech.gestiondestock.services;

import com.tech.gestiondestock.dto.CommandeClientDto;
import com.tech.gestiondestock.dto.LigneCommandeClientDto;
import com.tech.gestiondestock.models.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

	CommandeClientDto save(CommandeClientDto dto);

	  CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);

	  CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);

	  CommandeClientDto updateClient(Integer idCommande, Integer idClient);

	  CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer newIdArticle);

	  CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);

	  CommandeClientDto findById(Integer id);

	  CommandeClientDto findByCode(String code);

	  List<CommandeClientDto> findAll();

	  List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande);

	  void delete(Integer id);
}
