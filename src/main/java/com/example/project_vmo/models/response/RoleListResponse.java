package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.AccountDto;
import java.util.List;
import lombok.Data;

@Data
public class RoleListResponse {
  private List<AccountDto> content;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;

}
