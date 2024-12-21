package com.tech.gestiondestock.repository;

import com.tech.gestiondestock.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Integer> {

    Optional<Category> findByCode(String code);
}
