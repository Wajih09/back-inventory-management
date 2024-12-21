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
@Service("ArticleServiceImpl1")
@Slf4j
public class ArticleServiceImpl1 implements ArticleService {
													
	private ArticleDao articleDao;
	@Autowired
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
	public ArticleDto save(ArticleDto articleDto) {
		List<String> errors = ArticleValidator.validate(articleDto);
		System.out.println(String.format("articleDto = %s", articleDto));
		if (!errors.isEmpty()) {
			log.error("Article is not valid {}", articleDto);
			throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
		}
		Article savedArticle = articleDao.save(ArticleDto.toEntity(articleDto));														
		System.out.println(String.format("savedArticle = %s", savedArticle));
		return ArticleDto.fromEntity(savedArticle);
	}

	@Override
	public ArticleDto findById(Integer id) {
		if (id == null) {
			log.error("L'Id de l'article est null");
			return null; 
		}

		log.debug("Searching for article with ID: {}", id);
		Optional<Article> article = articleDao.findById(id);
		return ArticleDto.fromEntity(article.orElseThrow(() -> {
			log.error("L'article avec ID {} n'est pas trouvé dans la BDD", id);
			return new EntityNotFoundException("L'article avec ID " + id + " n'est pas trouvé dans la BDD",
					ErrorCodes.ARTICLE_NOT_FOUND);
		})); 
	}

	@Override
	public ArticleDto findByCodeArticle(String codeArticle) {
		if (!StringUtils.hasLength(codeArticle)) {
			log.error("Le code de l'article est null");
			return null;
		}

		Optional<Article> article = articleDao.findByCodeArticle(codeArticle);
		return ArticleDto.fromEntity(article.orElseThrow(() -> new EntityNotFoundException(
				"L'article avec code " + codeArticle + " n'est pas trouvé dans la BDD", ErrorCodes.ARTICLE_NOT_FOUND)));
	}

	@Override
	public List<ArticleDto> findAll() {
		List<Article> articles = articleDao.findAll();
		return articles.stream().map(ArticleDto::fromEntity).collect(Collectors.toList());
	}

	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("L'ID de l'article est null");
			return;
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
