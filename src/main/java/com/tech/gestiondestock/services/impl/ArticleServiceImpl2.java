package com.tech.gestiondestock.services.impl;

import com.tech.gestiondestock.dto.ArticleDto;
import com.tech.gestiondestock.repository.ArticleDao;
import com.tech.gestiondestock.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ArticleServiceImpl2")
public class ArticleServiceImpl2 implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        return null;
    }

    @Override
    public ArticleDto findById(Integer id) {
        return null;
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return null;
    }

    @Override
    public List<ArticleDto> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
