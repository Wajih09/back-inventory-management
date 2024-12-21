package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleDao extends JpaRepository<Article, Integer> {

    @Query("select a from Article a where a.codeArticle = :code and a.designation = :designation")
    List<Article> findByCustomQuery(@Param("code") String c, @Param("designation") String d);//v11 min28

    @Query(value = "select * from article where code = :code", nativeQuery = true) // pas besoin de convertir la requete, elle est deja en native sql par expl
    List<Article> findByCustomNativeQuery(String code); //v11 no need for @Param("code") if you put the same name


    List<Article> findByCodeArticleIgnoreCaseAndDesignationIgnoreCase(String codeArticle, String designation); //v11 IgnoreCase means we cans search using low case or upper case

    Optional<Article> findByCodeArticle(String codeArticle);
}
