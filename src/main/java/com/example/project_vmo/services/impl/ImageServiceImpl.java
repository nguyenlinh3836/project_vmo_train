package com.example.project_vmo.services.impl;


import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.repositories.ImageRepo;
import com.example.project_vmo.services.ImageService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
  @Autowired
  private ImageRepo imageRepo;

  @Override
  public Image saveImage(Image image) {
    return imageRepo.save(image);
  }

  @Override
  public List<Image> createImage(List<Image> images) {
    return imageRepo.saveAll(images);
  }

  @Override
  public List<Image> updateImage(List<Image> images) {
    return imageRepo.saveAll(images);
  }

  @Override
  public void deleteImageAll(List<Image> images) {
    imageRepo.deleteAll(images);
  }


}
