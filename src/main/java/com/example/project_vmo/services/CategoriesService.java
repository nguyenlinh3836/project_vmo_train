package com.example.project_vmo.services;

import com.example.project_vmo.models.request.CategoriesRequest;
import com.example.project_vmo.models.response.CategoriesResponse;


public interface CategoriesService {
  CategoriesResponse listCategory(int pageNo, int pageSize,String sortBy);
  CategoriesRequest createCategory(CategoriesRequest categoriesRequest);
  CategoriesRequest updateCategory(CategoriesRequest categoriesRequest,int id);
  void deleteCategory(int id);

}
