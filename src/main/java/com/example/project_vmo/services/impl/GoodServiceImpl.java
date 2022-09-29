package com.example.project_vmo.services.impl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.project_vmo.commons.config.MapperUtil;
import com.example.project_vmo.commons.exception.ResourceNotFoundException;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.response.GoodResponse;
import com.example.project_vmo.models.response.MessageResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.GoodRepo;
import com.example.project_vmo.repositories.ImageRepo;
import com.example.project_vmo.services.GoodService;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class GoodServiceImpl implements GoodService {

  @Autowired
  private GoodRepo goodRepo;
  @Autowired
  private ImageRepo imageRepo;

  @Autowired
  private AccountRepo accountRepo;

  @Autowired
  private Cloudinary cloudinary;

  public static String uploadDir =
      System.getProperty("user.dir") + "/src/main/resources/static/images";


  @Override
  @Transactional
  public GoodResponse getAllGoods(int pageNo,int pageSize) {
    Page<Good> goods = goodRepo.findAll(PageRequest.of(pageNo,pageSize));
    List<GoodDto> content = goods.getContent().stream().map(good -> MapperUtil.map(good,GoodDto.class)).collect(Collectors.toList());
    GoodResponse response = new GoodResponse();
    response.setContent(content);
    response.setPageNo(pageNo);
    response.setPageSize(pageSize);
    response.setTotalElements(goods.getTotalElements());
    response.setTotalPages(goods.getTotalPages());
    response.setLast(goods.isLast());
    response.setCode(HttpStatus.ACCEPTED.value());
    return response;
  }

  @Override
  @Transactional
  public GoodDto findByGoodName(String name) {
    Good good = goodRepo.findByGoodsName(name);
    return MapperUtil.map(good,GoodDto.class);
  }

  @Override
  public GoodDto getGoodById(int id) {
    return MapperUtil.map(goodRepo.findByGoodsId(id), GoodDto.class);
  }

  @Override
  @Transactional
  public GoodDto createGood(GoodDto goodDto,MultipartFile[] files)  {
    Good good = MapperUtil.map(goodDto, Good.class);
    List<Image> image = new ArrayList<>();
    for (MultipartFile item : files) {
      try {
        {
          Map result = upload(item);
          Image dbImage = new Image();
          dbImage.setName((String)result.get("original_filename"));
          dbImage.setFileType(item.getContentType());
          dbImage.setImageUrl((String)result.get("url"));
          dbImage.setGoods(good);
          image.add(dbImage);
        }
      }catch (IOException e){
        e.printStackTrace();
      }
    }
    imageRepo.saveAll(image);
    good.setImages(image);
    Account account = accountRepo.findByAccountId(goodDto.getSupplierId());
    good.setAccount(account);
    return MapperUtil.map(goodRepo.save(good), GoodDto.class);
  }

  @Override
  @Transactional
  public GoodDto updateGood(GoodDto goodDto, int id, MultipartFile[] files, User user) throws IOException {
    if (id<=0){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"id must >= 1");
    }
    Good good = MapperUtil.map(goodRepo.findByGoodsId(id), Good.class);
    if (good.getAccount().getAccountId() == accountRepo.findByUsername(user.getUsername()).getAccountId()){
      imageRepo.deleteImagesByGoods(good.getGoodsId());
      return createGood(goodDto,files);
    } else {
      throw new ResourceNotFoundException("You not own this good !");
    }
  }

  @Override
  @Transactional
  public void deleteGood(int id,User user) {
    if (id<=0){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"id must >= 1");
    }
    Good good = goodRepo.findByGoodsId(id);
    if (good.getAccount().getAccountId() == accountRepo.findByUsername(user.getUsername()).getAccountId()){
      good.setIs_deleted(true);
      goodRepo.save(good);
    } else {
      throw new ResourceNotFoundException("You not own this good !");
    }
  }

  public Map upload(MultipartFile multipartFile) throws IOException {
    File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    FileOutputStream fo = new FileOutputStream(file);
    fo.write(multipartFile.getBytes());
    fo.close();
    Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
    return result;
  }
}
