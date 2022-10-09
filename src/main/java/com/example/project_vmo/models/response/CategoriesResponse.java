package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.CategoriesRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesResponse {
  private int code;
  private List<CategoriesRequest> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;
}
