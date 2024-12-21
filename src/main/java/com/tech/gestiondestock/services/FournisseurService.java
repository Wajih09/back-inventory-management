package com.tech.gestiondestock.services;

import java.util.List;

import com.tech.gestiondestock.dto.FournisseurDto;

public interface FournisseurService {
    FournisseurDto save(FournisseurDto dto);

    FournisseurDto findById(Integer id);

    List<FournisseurDto> findAll();

    void delete(Integer id);
}
