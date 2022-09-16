package com.example.project_vmo.repositories;


import com.example.project_vmo.models.entities.Image;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepo extends JpaRepository<Image, Integer> {

  Image findById(int id);

  @Modifying
  @Transactional
  @Query("DELETE FROM Image i WHERE i.goods.goodsId = ?1")
  void deleteImagesByGoods(int goodId);
}
