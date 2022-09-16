package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.UserStaticDto;
import java.util.List;
import lombok.ToString;

public class ListUserStatist {
  @ToString.Exclude
  private List<UserStaticDto> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;

}
