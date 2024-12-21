package com.tech.gestiondestock.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tech.gestiondestock.dto.CategoryDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import com.tech.gestiondestock.services.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

	@Autowired
	private CategoryService categoryService;

	@Test
	public void shouldSaveCategoryWithSuccess() {
		CategoryDto expectedCategory = CategoryDto.builder().code("Cat test").designation("Designation test")
				.idEntreprise(1).build();

		CategoryDto savedCategory = categoryService.save(expectedCategory);

		assertNotNull(savedCategory, "Saved category should not be null");
		assertNotNull(savedCategory.getId(), "Saved category ID should not be null");

		assertAll("Saved category", () -> assertNotNull(savedCategory, "Saved category should not be null"),
				() -> assertNotNull(savedCategory.getId(), "Saved category ID should not be null"),
				() -> assertEquals(expectedCategory.getIdEntreprise(), savedCategory.getIdEntreprise(),
						"ID entreprise should match"),
				() -> assertEquals(expectedCategory.getCode(), savedCategory.getCode(), "Category code should match"),
				() -> assertEquals(expectedCategory.getDesignation(), savedCategory.getDesignation(),
						"Category designation should match"));
	}

	@Test
	public void shouldUpdateCategoryWithSuccess() {
		CategoryDto expectedCategory = CategoryDto.builder().code("Cat test").designation("Designation test")
				.idEntreprise(1).build();

		CategoryDto savedCategory = categoryService.save(expectedCategory);

		CategoryDto categoryToUpdate = savedCategory;
		categoryToUpdate.setCode("Cat update");

		savedCategory = categoryService.save(categoryToUpdate);
		
		assertNotNull(categoryToUpdate, "Updated category should not be null");
		assertNotNull(categoryToUpdate.getId(), "Updated category ID should not be null");

		final CategoryDto finalSavedCategory = savedCategory; // Final variable to hold the updated category
		assertAll("Updated category",
		    () -> assertNotNull(finalSavedCategory, "Saved category should not be null after update"),
		    () -> assertNotNull(finalSavedCategory.getId(), "Saved category ID should not be null after update"),
		    () -> assertEquals(categoryToUpdate.getId(), finalSavedCategory.getId(), "Category ID should remain unchanged after update"),
		    () -> assertEquals(categoryToUpdate.getIdEntreprise(), finalSavedCategory.getIdEntreprise(), "ID entreprise should remain unchanged after update"),
		    () -> assertEquals(categoryToUpdate.getCode(), finalSavedCategory.getCode(), "Category code should be updated"),
		    () -> assertEquals(categoryToUpdate.getDesignation(), finalSavedCategory.getDesignation(), "Category designation should be updated")
		);
	}
	
	@Test
	  public void shouldThrowInvalidEntityException() {
	    CategoryDto expectedCategory = CategoryDto.builder().build();

	    InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> categoryService.save(expectedCategory));

	    assertEquals(ErrorCodes.CATEGORY_NOT_VALID, expectedException.getErrorCodes());
	    assertEquals(2, expectedException.getErrors().size());
	    assertEquals("Veuillez renseigner le code de la catégorie", expectedException.getErrors().get(0));
	  }

	  @Test
	  public void shouldThrowEntityNotFoundException() {
	    EntityNotFoundException expectedException = assertThrows(EntityNotFoundException.class, () -> categoryService.findById(0));

	    assertEquals(ErrorCodes.CATEGORY_NOT_FOUND, expectedException.getErrorCodes());
	    assertEquals("Category avec ID 0 n'est pas trouvé dans la BDD", expectedException.getMessage());
	  }

	  @Test
	  public void shouldThrowEntityNotFoundException2() {
	      assertThrows(EntityNotFoundException.class, () -> {
	          categoryService.findById(0);
	      });
	  }

	
	
}
