package com.example.project_vmo.services;

import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.response.GoodResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface GoodService {

  GoodResponse getAllGoods(int pageNo, int pageSize);

  GoodDto findByGoodName(String name);

  GoodDto getGoodById(int id);

  GoodDto createGood(GoodDto good,MultipartFile[] files) throws IOException;

  GoodDto updateGood(GoodDto good, int id,MultipartFile[] files) throws IOException;

  void deleteGood(int id);

}
