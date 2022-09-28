package com.example.project_vmo.models.response;

import com.example.project_vmo.models.request.AccountRequest;
import java.util.List;
import lombok.Data;

@Data
public class RoleListResponse {
  private List<AccountResponse> content;
  private int code;
  private int pageNo;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean last;

}
