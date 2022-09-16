package com.example.project_vmo.services;


import com.example.project_vmo.models.entities.Image;
import java.util.List;

public interface ImageService {

  Image saveImage(Image image);

  List<Image> createImage(List<Image> images);

  List<Image> updateImage(List<Image> images);

  void deleteImageAll(List<Image> images);

}
