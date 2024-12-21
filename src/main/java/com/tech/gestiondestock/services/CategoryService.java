package com.tech.gestiondestock.services;

import com.tech.gestiondestock.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto categoryDto);
    CategoryDto findById(Integer id);
    CategoryDto findBycode(String code);
    List<CategoryDto> findAll();
    void delete(Integer id);
}
