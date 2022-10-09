package com.example.project_vmo.repositories;

import com.example.project_vmo.models.entities.Categories;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Categories,Integer> {
  Categories findByCategoryId(int id);
  Categories findByCategoryName(String name);
}
