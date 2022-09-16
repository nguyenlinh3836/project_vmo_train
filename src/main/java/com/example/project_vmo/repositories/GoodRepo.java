package com.example.project_vmo.repositories;


import com.example.project_vmo.models.entities.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepo extends JpaRepository<Good,Integer> {
  Good findByGoodsName(String name);
  Good findByGoodsId(int id);


}
