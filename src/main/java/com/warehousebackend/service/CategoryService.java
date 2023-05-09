package com.warehousebackend.service;


import com.warehousebackend.model.entities.Category;
import com.warehousebackend.model.entities.enums.CategoryNameEnum;

import java.util.List;

public interface CategoryService {
    Category findByName(CategoryNameEnum category);

    List<Category> initCategories();
}
