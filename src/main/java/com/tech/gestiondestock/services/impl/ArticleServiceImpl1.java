package com.tech.gestiondestock.services.impl;

import com.tech.gestiondestock.dto.ArticleDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.exception.InvalidOperationException;
import com.tech.gestiondestock.models.Article;
import com.tech.gestiondestock.models.LigneCommandeClient;
import com.tech.gestiondestock.models.LigneCommandeFournisseur;
import com.tech.gestiondestock.models.LigneVente;
import com.tech.gestiondestock.repository.ArticleDao;
import com.tech.gestiondestock.repository.CommandeFournisseurDao;
import com.tech.gestiondestock.repository.LigneCommandeClientDao;
import com.tech.gestiondestock.repository.LigneCommandeFournisseurDao;
import com.tech.gestiondestock.repository.LigneVenteDao;
import com.tech.gestiondestock.services.ArticleService;
import com.tech.gestiondestock.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Primary
@Service("ArticleServiceImpl1") // on donne un nome à ce service pour dire à Spring que cette classe est un
								// service et ici on donne un nom à ce service pour pouvoir utiliser qualified
@Slf4j // v11 min19 system log il va nous fournir un logger
public class ArticleServiceImpl1 implements ArticleService {// v12 min13:40 on fait le systeme interface et
															// implementation car si on veut définir une nouvelle regle
															// de gestion de la methode save, il faut pas modifier la
															// classe existante mais on crée une variable de type
															// interface et on l'instance avec la classe
	// v12 min18:28 cad l'application doit etre fermé à la modification, ouverte à
	// l'extention
	private ArticleDao articleDao;
	@Autowired // v12 min20 injection par constructeur
	// v12 min52 constructor injection : lors de la création de la classe, spring va
	// injecter automatiquement toutes les dépendances déclarées dans le
	// constructeur, ici le reposetrey qu'on a besoin dans les methodes qui suient
	private LigneVenteDao ligneVenteDao;
	private LigneCommandeFournisseurDao ligneCommandeFournisseurDao;
	private LigneCommandeClientDao ligneCommandeClientDao;

	@Autowired
	public ArticleServiceImpl1(ArticleDao articleDao, LigneVenteDao ligneVenteDao,
			LigneCommandeFournisseurDao ligneCommandeFournisseurDao, LigneCommandeClientDao ligneCommandeClientDao) {
		this.articleDao = articleDao;
		this.ligneVenteDao = ligneVenteDao;
		this.ligneCommandeFournisseurDao = ligneCommandeFournisseurDao;
		this.ligneCommandeClientDao = ligneCommandeClientDao;
	}

	@Override
	public ArticleDto save(ArticleDto articleDto) { // enregistrer dans la bdd
		List<String> errors = ArticleValidator.validate(articleDto);
		System.out.println(String.format("articleDto = %s", articleDto));
		if (!errors.isEmpty()) {
			log.error("Article is not valid {}", articleDto);
			throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
		}
		Article savedArticle = articleDao.save(ArticleDto.toEntity(articleDto));// v11 min28 car save de dao retourne
																				// entity et prend entity non dto comme
																				// dans l'interface service qu'on est
																				// entrain de construire içi *******
		System.out.println(String.format("savedArticle = %s", savedArticle));
		return ArticleDto.fromEntity(savedArticle);
	}

	@Override
	public ArticleDto findById(Integer id) {
		if (id == null) {
			log.error("L'Id de l'article est null");
			return null; // ya pas de throw excep ici parce que on parle pas de entité invalide ou not
							// found mais de id dès le départ
		}

		log.debug("Searching for article with ID: {}", id);
		Optional<Article> article = articleDao.findById(id);
		return ArticleDto.fromEntity(article.orElseThrow(() -> {
			log.error("L'article avec ID {} n'est pas trouvé dans la BDD", id);
			return new EntityNotFoundException("L'article avec ID " + id + " n'est pas trouvé dans la BDD",
					ErrorCodes.ARTICLE_NOT_FOUND);
		})); // v7 min30 cad on va retourner l'article si nn une exception (tous dans la meme
				// ligne)

	}

	@Override
	public ArticleDto findByCodeArticle(String codeArticle) {
		if (!StringUtils.hasLength(codeArticle)) {
			log.error("Le code de l'article est null");
			return null;
		}

		Optional<Article> article = articleDao.findByCodeArticle(codeArticle);
		return ArticleDto.fromEntity(article.orElseThrow(() -> new EntityNotFoundException(
				"L'article avec code " + codeArticle + " n'est pas trouvé dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND))); // v7
																															// min36
		// dans interface service tout les return types sont dto, si tu veux changer en
		// optional, change dans le dao
	}

	@Override
	public List<ArticleDto> findAll() {
		List<Article> articles = articleDao.findAll();
		return articles.stream().map(ArticleDto::fromEntity).collect(Collectors.toList()); // v12 min37 methode
																							// reference here or can be
																							// map(el ->
																							// ArticleDto.fromEntity(el))
	}

	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("L'ID de l'article est null");
			return; // sec comme ca
		}
		List<LigneCommandeClient> ligneCommandeClients = ligneCommandeClientDao.findAllByArticleId(id);
		if (!ligneCommandeClients.isEmpty()) {
			throw new InvalidOperationException(
					"Impossible de supprimer un article deja utilise dans des commandes client",
					ErrorCodes.ARTICLE_ALREADY_IN_USE);
		}
		List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurDao.findAllByArticleId(id);
		if (!ligneCommandeFournisseurs.isEmpty()) {
			throw new InvalidOperationException(
					"Impossible de supprimer un article deja utilise dans des commandes fournisseur",
					ErrorCodes.ARTICLE_ALREADY_IN_USE);
		}
		List<LigneVente> ligneVentes = ligneVenteDao.findAllByArticleId(id);
		if (!ligneVentes.isEmpty()) {
			throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes",
					ErrorCodes.ARTICLE_ALREADY_IN_USE);
		}
		articleDao.deleteById(id);
	}

}
