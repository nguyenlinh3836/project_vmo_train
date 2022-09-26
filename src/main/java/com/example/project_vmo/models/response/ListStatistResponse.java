package com.example.project_vmo.models.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListStatistResponse {
  private int code;
  private List<UserStatistResponse> content;
  private int pageNo;
  private int pageSize;
  private int totalCount;
  private int totalPages;
  private boolean last;
}
