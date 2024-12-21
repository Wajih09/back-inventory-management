package com.tech.gestiondestock.services.impl;

import com.tech.gestiondestock.dto.ArticleDto;
import com.tech.gestiondestock.dto.ClientDto;
import com.tech.gestiondestock.dto.CommandeClientDto;
import com.tech.gestiondestock.dto.LigneCommandeClientDto;
import com.tech.gestiondestock.dto.MvtStkDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.exception.InvalidOperationException;
import com.tech.gestiondestock.models.Article;
import com.tech.gestiondestock.models.Client;
import com.tech.gestiondestock.models.CommandeClient;
import com.tech.gestiondestock.models.EtatCommande;
import com.tech.gestiondestock.models.LigneCommandeClient;
import com.tech.gestiondestock.models.SourceMvtStk;
import com.tech.gestiondestock.models.TypeMvtStk;
import com.tech.gestiondestock.repository.ArticleDao;
import com.tech.gestiondestock.repository.ClientDao;
import com.tech.gestiondestock.repository.CommandeClientDao;
import com.tech.gestiondestock.repository.LigneCommandeClientDao;
import com.tech.gestiondestock.services.CommandeClientService;
import com.tech.gestiondestock.services.MvtStkService;
import com.tech.gestiondestock.validator.ArticleValidator;
import com.tech.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeClientService {

    private CommandeClientDao commandeClientDao;
    private LigneCommandeClientDao ligneCommandeClientDao ;
    private ClientDao clientDao;
    private ArticleDao articleDao;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeClientServiceImpl(CommandeClientDao commandeClientDao, ClientDao clientDao, ArticleDao articleDao,LigneCommandeClientDao ligneCommandeClientDao, MvtStkService mvtStkService) {
        this.commandeClientDao = commandeClientDao;
        this.ligneCommandeClientDao = ligneCommandeClientDao;
        this.clientDao = clientDao;
        this.articleDao = articleDao;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeClientDto save(CommandeClientDto commandeClientDto) {
        List<String> errors = CommandeClientValidator.validate(commandeClientDto);
        if(!errors.isEmpty()){ //v13 min53 verification 1er niveau
            log.error("Commande client is not valid");
            throw new InvalidEntityException("La commande client n'est pas valide", ErrorCodes.COMMANDE_CLIENT_NOT_VALID, errors);
        }
        //verification metier v13 min45
        Optional<Client> client = clientDao.findById(commandeClientDto.getClient().getId()); //ici le dto est deja validé cad c garanti que le champs client
        //n'et pas null donc on passe à cette étape la ou on doit verifier si le client existe dans la BDD ou pas
        //v13 min23 ici on veut chercher client dans la bdd donc on utilise clientDao et pas commandeClientDao
                                                                            // et l'id sera extrait du client dans le dto
        if(!client.isPresent()){ //Can be replaced with 'isEmpty()' equiv to oeElseThrow in ArticleServiceImpl1 means if it's not in the bdd
            log.warn("Client with ID {} was not found in DB ", commandeClientDto.getClient().getId());
            throw new EntityNotFoundException("Le client avec ID " + commandeClientDto.getClient().getId() + "n'est pas trouvé dans la BDD", ErrorCodes.CLIENT_NOT_FOUND); //3eme param toujours quand il ya verirification de la list des erreurs
        }
        //on va vérifier les lignes de commandes de client malgré que json igonre mentionné dans le dto => vas dans la ligneCommandeClientDto => valider alors que article existe dans la bdd
        List<String> articleErrors = new ArrayList<>();
        if(commandeClientDto.getLignesCommandeClients() != null){ //v13 min28 ici on compare à null par if car à cet étape là on a déja valider la commandeClient
            //cad (id, code, dateCreation, client) mais le champ ligne commande peut etre null, donc ici on veut dire que si on recoit ce champs
            //dans la CommandeClientDto donc on doit vérifier si ça contienne un article dans la BD
            commandeClientDto.getLignesCommandeClients().forEach(el -> {
               if(el.getArticle() != null){
                   Optional<Article> article = articleDao.findById(el.getArticle().getId()); // v13 m31 on cherche l'article dans la bd est pas la ligne de commande
                   //donc articleDao et pas ligneCommandeClientDao

                   if(article.isEmpty()){
                       articleErrors.add("L'article avec l'ID " + el.getArticle().getId() + "n'esiste pas"); // ici on crée une liste dans la ligne 55
                       //et puis on faire remplir par message si erreur existe, la cause de list car on boucle sur plusieur articles et pas un seul comme un seul
                       //client dans la commandeClient auparavant.
                   }
               }
               else {
                   articleErrors.add("Impossible d'enregistrer une commande avec un article null"); //v13 min48
               }
            });
        }
        if(!articleErrors.isEmpty()){
            log.warn("L'article n'existe pas dans la BD");
            throw new EntityNotFoundException("L'article n'existe pas dans la BD", ErrorCodes.ARTICLE_NOT_FOUND);
        }

        //v13 min36 si tous va bien on enregistre la commande client dans la BD
        CommandeClient savedCommandeClient = commandeClientDao.save(CommandeClientDto.toEntity(commandeClientDto));
        //CommandeClientDto.fromEntity(savedCommandeClient); // if it's error must extend Integer => you should reverse entity and Integer in JpaDao< ... >

        if(commandeClientDto.getLignesCommandeClients() != null) { //v13 min43
            //on affecte la commande client (un champ) aux lignes des commandes
            commandeClientDto.getLignesCommandeClients().forEach(lcc -> {
                LigneCommandeClient ligneCommandeClient = LigneCommandeClientDto.toEntity(lcc);
                ligneCommandeClient.setCommandeClient(savedCommandeClient); //ceci est le coeur de l'action : on affecte à l'entity le saved commande client
                ligneCommandeClientDao.save(ligneCommandeClient); // ici on enregistre ces lignes de commandes eux memes dans la BDD
            });
        }
        return CommandeClientDto.fromEntity(savedCommandeClient); //recap v13 min44
    }

    @Override
    public CommandeClientDto findById(Integer id) {
        if(id == null){
            log.error("L'Id de la commande client est null");
            return null; // ya pas de throw excep ici parceque on parle pas de entité invalide ou not found mais de id dès le départ
        }
        return commandeClientDao.findById(id).map(CommandeClientDto::fromEntity). //v13 min53
                orElseThrow(() -> new EntityNotFoundException("La commande client avec ID " + id +" n'est pas trouvé dans la BDD", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public CommandeClientDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Le code de la commande client est null");
            return null; // ya pas de throw excep ici parceque on parle pas de entité invalide ou not found mais de id dès le départ
        }
        return commandeClientDao.findByCode(code).map(CommandeClientDto::fromEntity). //v13 min53
                orElseThrow(() -> new EntityNotFoundException("La commande client avec code " + code +" n'est pas trouvé dans la BDD", ErrorCodes.COMMANDE_CLIENT_NOT_FOUND));
    }

    @Override
    public List<CommandeClientDto> findAll() {
        return commandeClientDao.findAll().stream().map(CommandeClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error(("Delete ID is null"));
            return;
        }
        commandeClientDao.deleteById(id); //deleteById() and not delete()
    }

    @Override
    public List<LigneCommandeClientDto> findAllLignesCommandesClientByCommandeClientId(Integer idCommande) {
      return ligneCommandeClientDao.findAllByCommandeClientId(idCommande).stream()
          .map(LigneCommandeClientDto::fromEntity)
          .collect(Collectors.toList());
    }

    @Override
    public CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
      checkIdCommande(idCommande);
      if (!StringUtils.hasLength(String.valueOf(etatCommande))) {
        log.error("L'etat de la commande client is NULL");
        throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null",
            ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }
      CommandeClientDto commandeClient = checkEtatCommande(idCommande);
      commandeClient.setEtatCommande(etatCommande);

      CommandeClient savedCmdClt = commandeClientDao.save(CommandeClientDto.toEntity(commandeClient));
      if (commandeClient.isCommandeLivree()) {
        updateMvtStk(idCommande);
      }

      return CommandeClientDto.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {
      checkIdCommande(idCommande);
      checkIdLigneCommande(idLigneCommande);

      if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0) {
        log.error("L'ID de la ligne commande is NULL");
        throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou ZERO",
            ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }

      CommandeClientDto commandeClient = checkEtatCommande(idCommande);
      Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(idLigneCommande);

      LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
      ligneCommandeClient.setQuantite(quantite);
      ligneCommandeClientDao.save(ligneCommandeClient);

      return commandeClient;
    }

    @Override
    public CommandeClientDto updateClient(Integer idCommande, Integer idClient) {
      checkIdCommande(idCommande);
      if (idClient == null) {
        log.error("L'ID du client is NULL");
        throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID client null",
            ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }
      CommandeClientDto commandeClient = checkEtatCommande(idCommande);
      Optional<Client> clientOptional = clientDao.findById(idClient);
      if (clientOptional.isEmpty()) {
        throw new EntityNotFoundException(
            "Aucun client n'a ete trouve avec l'ID " + idClient, ErrorCodes.CLIENT_NOT_FOUND);
      }
      commandeClient.setClient(ClientDto.fromEntity(clientOptional.get()));

      return CommandeClientDto.fromEntity(
          commandeClientDao.save(CommandeClientDto.toEntity(commandeClient))
      );
    }

    @Override
    public CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
      checkIdCommande(idCommande);
      checkIdLigneCommande(idLigneCommande);
      checkIdArticle(idArticle, "nouvel");

      CommandeClientDto commandeClient = checkEtatCommande(idCommande);

      Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(idLigneCommande);

      Optional<Article> articleOptional = articleDao.findById(idArticle);
      if (articleOptional.isEmpty()) {
        throw new EntityNotFoundException(
            "Aucune article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
      }

      List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));
      if (!errors.isEmpty()) {
        throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
      }

      LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
      ligneCommandeClientToSaved.setArticle(articleOptional.get());
      ligneCommandeClientDao.save(ligneCommandeClientToSaved);

      return commandeClient;
    }

    @Override
    public CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
      checkIdCommande(idCommande);
      checkIdLigneCommande(idLigneCommande);

      CommandeClientDto commandeClient = checkEtatCommande(idCommande);
      // Just to check the LigneCommandeClient and inform the client in case it is absent
      findLigneCommandeClient(idLigneCommande);
      ligneCommandeClientDao.deleteById(idLigneCommande);

      return commandeClient;
    }

    private CommandeClientDto checkEtatCommande(Integer idCommande) {
      CommandeClientDto commandeClient = findById(idCommande);
      if (commandeClient.isCommandeLivree()) {
        throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livree", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }
      return commandeClient;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer idLigneCommande) {
      Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientDao.findById(idLigneCommande);
      if (ligneCommandeClientOptional.isEmpty()) {
        throw new EntityNotFoundException(
            "Aucune ligne commande client n'a ete trouve avec l'ID " + idLigneCommande, ErrorCodes.COMMANDE_CLIENT_NOT_FOUND);
      }
      return ligneCommandeClientOptional;
    }

    private void checkIdCommande(Integer idCommande) {
      if (idCommande == null) {
        log.error("Commande client ID is NULL");
        throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID null",
            ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }
    }

    private void checkIdLigneCommande(Integer idLigneCommande) {
      if (idLigneCommande == null) {
        log.error("L'ID de la ligne commande is NULL");
        throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null",
            ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }
    }

    private void checkIdArticle(Integer idArticle, String msg) {
      if (idArticle == null) {
        log.error("L'ID de " + msg + " is NULL");
        throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null",
            ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
      }
    }

    private void updateMvtStk(Integer idCommande) {
      List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientDao.findAllByCommandeClientId(idCommande);
      ligneCommandeClients.forEach(lig -> {
        effectuerSortie(lig);
      });
    }

    private void effectuerSortie(LigneCommandeClient lig) {
      MvtStkDto mvtStkDto = MvtStkDto.builder()
          .article(ArticleDto.fromEntity(lig.getArticle()))
          .dateMvt(Instant.now())
          .typeMvt(TypeMvtStk.SORTIE)
          .sourceMvt(SourceMvtStk.COMMANDE_CLIENT)
          .quantite(lig.getQuantite())
          .idEntreprise(lig.getIdEntreprise())
          .build();
      mvtStkService.sortieStock(mvtStkDto);
    }

}
