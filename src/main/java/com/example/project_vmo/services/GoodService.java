package com.example.project_vmo.services;

import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.response.GoodResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

public interface GoodService {

  GoodResponse getAllGoods(int pageNo, int pageSize,String sortBy);

  GoodDto findByGoodName(String name);

  GoodDto getGoodById(int id);

  GoodDto createGood(GoodDto good,MultipartFile[] files) throws IOException;

  GoodDto updateGood(GoodDto good, int id,MultipartFile[] files, User user) throws IOException;

  void deleteGood(int id,User user);

}
