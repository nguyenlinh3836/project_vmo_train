package com.example.project_vmo.models.response;

import com.example.project_vmo.models.entities.Categories;
import com.example.project_vmo.models.request.CategoriesRequest;
import com.example.project_vmo.models.request.ImageDto;
import java.util.Date;
import java.util.List;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodResponseItem {
  private int goodsId;
  private String goodsName;
  private double price;
  private List<ImageDto> images;
  private CategoriesRequest categories;
  private String supplierName;
  private Date createAt;
}
