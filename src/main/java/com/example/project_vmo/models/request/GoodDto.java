package com.example.project_vmo.models.request;

import com.sun.istack.NotNull;
import java.util.Date;
import java.util.List;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodDto {

  @Id
  private int goodsId;
  @NotNull
  private String goodsName;
  @NotNull
  private int quantity;
  private List<ImageDto> images;
  private int supplierId;
  private Date create_at;
//  private Date updated_at;
//  private Boolean is_deleted = Boolean.FALSE;
//  private MultipartFile[] images;


}
