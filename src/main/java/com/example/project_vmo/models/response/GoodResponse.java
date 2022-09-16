package com.example.project_vmo.models.response;

import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.request.GoodDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodResponse {
  @ToString.Exclude
  private List<GoodDto> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;
}
