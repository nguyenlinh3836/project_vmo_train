package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.UserStatistDto;
import java.util.List;
import lombok.ToString;

public class ListUserStatist {
  @ToString.Exclude
  private List<UserStatistDto> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;

}
