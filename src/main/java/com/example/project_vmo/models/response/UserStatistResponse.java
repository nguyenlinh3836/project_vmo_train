package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.UserStatistDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistResponse {
  private List<UserStatistDto> content;
  private int pageNo;
  private int pageSize;
  private int totalCount;
  private int totalPages;
  private boolean last;
}
