package com.tech.gestiondestock.services.impl;

import com.tech.gestiondestock.dto.CategoryDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.repository.CategoryDao;
import com.tech.gestiondestock.services.CategoryService;
import com.tech.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if(!errors.isEmpty()){
            log.error("Category is not valid", categoryDto);
            throw new InvalidEntityException("La catégory n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
       }
       return CategoryDto.fromEntity(categoryDao.save(CategoryDto.toEntity(categoryDto)));
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id==null){
            log.error(("Category ID is null"));
            return null;
        }
        return CategoryDto.fromEntity(categoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Category avec ID " + id + " n'est pas trouvé dans la BDD", ErrorCodes.CATEGORY_NOT_FOUND)));
    }

    @Override
    public CategoryDto findBycode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error(("Category code is null"));
            return null;
        }
        return CategoryDto.fromEntity(categoryDao.findByCode(code).orElseThrow(() -> new EntityNotFoundException("Category avec code " + code + " n'est pas trouvé dans la BDD", ErrorCodes.CATEGORY_NOT_FOUND)));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(CategoryDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            log.error(("Delete ID is null"));
            return;
        }
        categoryDao.deleteById(id);
    }
}
