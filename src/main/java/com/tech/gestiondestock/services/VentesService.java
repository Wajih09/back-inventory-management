package com.tech.gestiondestock.services;

import com.tech.gestiondestock.dto.VentesDto;

import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto ventes);
    VentesDto findById(Integer id);
    VentesDto findByCode(String code);
    List<VentesDto> findAll();
    void delete(Integer id);
}
