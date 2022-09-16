package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.request.ImageDto;
import com.example.project_vmo.models.response.GoodResponse;
import com.example.project_vmo.repositories.GoodRepo;
import com.example.project_vmo.repositories.ImageRepo;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class GoodServiceImplTest {

  @Mock
  private GoodRepo mockGoodRepo;
  @Mock
  private ImageRepo mockImageRepo;

  @InjectMocks
  private GoodServiceImpl goodServiceImplUnderTest;

  @Test
  void testGetAllGoods() {
    // Setup
    final GoodResponse expectedResult = new GoodResponse(
        List.of(new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name")))), 0, 5, 1, 1,
        true);

    // Configure GoodRepo.findAll(...).
    final Page<Good> goods = new PageImpl<>(List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of(new Image(0, "name", null)))));
    when(mockGoodRepo.findAll(PageRequest.of(0, 5))).thenReturn(goods);

    // Run the test
    final GoodResponse result = goodServiceImplUnderTest.getAllGoods(0, 5);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAllGoods_GoodRepoReturnsNoItems() {
    // Setup
    final GoodResponse expectedResult = new GoodResponse(
        List.of(new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name")))), 0, 5, 0L, 1,
        true);
    when(mockGoodRepo.findAll(PageRequest.of(0, 5)))
        .thenReturn(new PageImpl<>(Collections.emptyList()));

    // Run the test
    final GoodResponse result = goodServiceImplUnderTest.getAllGoods(0, 5);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testFindByGoodName() {
    // Setup
    // Configure GoodRepo.findByGoodsName(...).
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.findByGoodsName("name")).thenReturn(good);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.findByGoodName("name");

    // Verify the results
  }

  @Test
  void testGetGoodById() {
    // Setup
    // Configure GoodRepo.findByGoodsId(...).
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.getGoodById(0);

    // Verify the results
  }

  @Test
  void testCreateGood() {
    // Setup
    final GoodDto goodDto = new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name")));
    final MultipartFile[] files = new MultipartFile[]{};

    // Configure ImageRepo.saveAll(...).
    final List<Image> images = List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of())));
    when(mockImageRepo.saveAll(List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of()))))).thenReturn(images);

    // Configure GoodRepo.save(...).
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        images);
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.createGood(goodDto, files);

    // Verify the results
    verify(mockImageRepo).saveAll(List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of()))));
  }

  @Test
  void testUpdateGood() throws Exception {
    // Setup
    final GoodDto goodDto = new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name")));
    final MultipartFile[] files = new MultipartFile[]{};

    // Configure GoodRepo.findByGoodsId(...).
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Configure ImageRepo.saveAll(...).
    final List<Image> images = List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of())));
    when(mockImageRepo.saveAll(List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of()))))).thenReturn(images);

    // Configure GoodRepo.save(...).
    final Good good1 = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good1);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.updateGood(goodDto, 0, files);

    // Verify the results
    verify(mockImageRepo).deleteImagesByGoods(0);
    verify(mockImageRepo).saveAll(List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of()))));
  }

  @Test
  void testUpdateGood_ThrowsIOException() {
    // Setup
    final GoodDto goodDto = new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name")));
    final MultipartFile[] files = new MultipartFile[]{};

    // Configure GoodRepo.findByGoodsId(...).
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Configure ImageRepo.saveAll(...).
    final List<Image> images = List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of())));
    when(mockImageRepo.saveAll(List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
            List.of()))))).thenReturn(images);

    // Configure GoodRepo.save(...).
    final Good good1 = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good1);

    // Run the test
    assertThatThrownBy(() -> goodServiceImplUnderTest.updateGood(goodDto, 0, files))
        .isInstanceOf(IOException.class);
    verify(mockImageRepo).deleteImagesByGoods(0);
    verify(mockImageRepo).saveAll(List.of(new Image(0, "name",
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of()))));
  }

  @Test
  void testDeleteGood() {
    // Setup
    // Configure GoodRepo.findByGoodsId(...).
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Configure GoodRepo.save(...).
    final Good good1 = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false,
        List.of(new Image(0, "name", null)));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good1);

    // Run the test
    goodServiceImplUnderTest.deleteGood(0);

    // Verify the results
    verify(mockGoodRepo).save(any(Good.class));
  }
}
