package com.example.project_vmo.controllers;

import com.example.project_vmo.models.request.CategoriesRequest;
import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.response.CategoriesResponse;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

  @Autowired
  private CategoriesService categoriesService;

  @GetMapping
  public CategoriesResponse listCategories(
      @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
      @RequestParam(defaultValue = "id",value = "sort") String name) {
    return categoriesService.listCategory(pageNo, pageSize, name);
  }

  @PostMapping()
  public ResponseEntity<?> createCategories(@RequestParam(value = "item") @Validated
  CategoriesRequest categoriesRequest) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(categoriesService.createCategory(categoriesRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateGood(
      @RequestPart(value = "good") CategoriesRequest categoriesRequest,
      @PathVariable("id") int id) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(categoriesService.updateCategory(categoriesRequest, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteGood(@PathVariable int id) {
    categoriesService.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(new MessageResponse(HttpStatus.ACCEPTED.value(), "Good has been deleted"));
  }
}
