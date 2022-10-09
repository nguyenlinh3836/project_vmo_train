package com.example.project_vmo.services.impl;

import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.commons.exception.ResourceAlreadyExistsException;
import com.example.project_vmo.commons.exception.ResourceNotFoundException;
import com.example.project_vmo.models.entities.Categories;
import com.example.project_vmo.models.request.CategoriesRequest;
import com.example.project_vmo.models.response.CategoriesResponse;
import com.example.project_vmo.repositories.CategoriesRepo;
import com.example.project_vmo.services.CategoriesService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoriesServiceImpl implements CategoriesService {

  @Autowired
  private CategoriesRepo categoriesRepo;

  @Override
  public CategoriesResponse listCategory(int pageNo, int pageSize, String sortBy) {
    Page<Categories> categories = categoriesRepo.findAll(
        PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
    List<CategoriesRequest> content = categories.getContent().stream()
        .map(category -> MapperUtil.map(category, CategoriesRequest.class)).collect(
            Collectors.toList());
    CategoriesResponse response = new CategoriesResponse();
    response.setContent(content);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalElements(categories.getTotalElements());
    response.setTotalPages(categories.getTotalPages());
    response.setLast(categories.isLast());
    response.setCode(HttpStatus.ACCEPTED.value());
    return response;
  }

  @Override
  public CategoriesRequest createCategory(CategoriesRequest categoriesRequest) {
    Categories categories = MapperUtil.map(categoriesRequest, Categories.class);
    return MapperUtil.map(categoriesRepo.save(categories), CategoriesRequest.class);
  }

  @Override
  public CategoriesRequest updateCategory(CategoriesRequest categoriesRequest, int id) {
    if (id<=0){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"id must >= 1");
    }
    Categories categories = categoriesRepo.findByCategoryId(id);
    if (categoriesRepo.findByCategoryName(categoriesRequest.getCategoryName()) == null) {
      categories.setCategoryName(categoriesRequest.getCategoryName());
      return MapperUtil.map(categoriesRepo.save(categories), CategoriesRequest.class);
    } else {
      throw new ResourceAlreadyExistsException("Category name exist !");
    }
  }

  @Override
  public void deleteCategory(int id) {
    if (id<=0){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"id must >= 1");
    }
    Categories categories = categoriesRepo.findByCategoryId(id);
    if (categories != null) {
      categories.set_delete(true);
      categoriesRepo.save(categories);
    } else {
      throw new ResourceNotFoundException("Not found category id");
    }
  }
}
