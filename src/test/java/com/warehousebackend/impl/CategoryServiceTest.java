package com.warehousebackend.impl;


import com.warehousebackend.model.entities.Category;
import com.warehousebackend.model.entities.enums.CategoryNameEnum;
import com.warehousebackend.model.repostiory.CategoryRepository;
import com.warehousebackend.service.CategoryService;
import com.warehousebackend.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.testng.AssertJUnit.assertEquals;

class CategoryServiceTest {
    private CategoryService categoryServiceTest;
    private CategoryRepository mockCategoryRepository;
    private Category category;

    @BeforeEach
    public void setUp() {
        mockCategoryRepository = mock(CategoryRepository.class);
        categoryServiceTest = new CategoryServiceImpl(mockCategoryRepository);
        category = new Category();
        category.setName(CategoryNameEnum.ACTIVE);
    }

    @Test
    void findByName_should_Work() {
        Mockito.when(mockCategoryRepository.findByName(CategoryNameEnum.ACTIVE)).
                thenReturn(Optional.of(category));
        Category byName = categoryServiceTest.findByName(CategoryNameEnum.ACTIVE);

        assertEquals(category.getName(), byName.getName());
    }

    @Test
    void initCategories_should_Work() {
        categoryServiceTest.initCategories();
        assertEquals(7, categoryServiceTest.initCategories().size());
    }
}
