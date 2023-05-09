package com.warehousebackend.model.repostiory;


import com.warehousebackend.model.entities.Category;
import com.warehousebackend.model.entities.enums.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryNameEnum category);
}
