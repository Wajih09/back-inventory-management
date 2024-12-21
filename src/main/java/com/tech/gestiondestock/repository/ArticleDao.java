package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleDao extends JpaRepository<Article, Integer> {

    @Query("select a from Article a where a.codeArticle = :code and a.designation = :designation")
    List<Article> findByCustomQuery(@Param("code") String c, @Param("designation") String d);

    @Query(value = "select * from article where code = :code", nativeQuery = true)
    List<Article> findByCustomNativeQuery(String code);


    List<Article> findByCodeArticleIgnoreCaseAndDesignationIgnoreCase(String codeArticle, String designation);

    Optional<Article> findByCodeArticle(String codeArticle);
}
