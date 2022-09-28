package com.example.project_vmo.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.project_vmo.models.entities.Account;
import com.example.project_vmo.models.entities.Good;
import com.example.project_vmo.models.entities.Image;
import com.example.project_vmo.models.entities.Role;
import com.example.project_vmo.models.entities.UserStatist;
import com.example.project_vmo.models.request.GoodDto;
import com.example.project_vmo.models.request.ImageDto;
import com.example.project_vmo.models.response.GoodResponse;
import com.example.project_vmo.repositories.AccountRepo;
import com.example.project_vmo.repositories.GoodRepo;
import com.example.project_vmo.repositories.ImageRepo;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
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
  @Mock
  private AccountRepo mockAccountRepo;
  @Mock
  private Cloudinary mockCloudinary;

  @InjectMocks
  private GoodServiceImpl goodServiceImplUnderTest;

  @Test
  void testGetAllGoods() {
    // Setup
    final GoodResponse expectedResult = new GoodResponse(0, List.of(
        new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name", "imageUrl", "imageType")), 0,
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), 0, 0, 0L, 0, false);

    // Configure GoodRepo.findAll(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Page<Good> goods = new PageImpl<>(List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Image(0, "name", "fileType", "imageUrl",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
                List.of()))));
    when(mockGoodRepo.findAll(PageRequest.of(0, 1))).thenReturn(goods);

    // Run the test
    final GoodResponse result = goodServiceImplUnderTest.getAllGoods(0, 0);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testGetAllGoods_GoodRepoReturnsNoItems() {
    // Setup
    final GoodResponse expectedResult = new GoodResponse(0, List.of(
        new GoodDto(0, "goodsName", 0, List.of(new ImageDto(0, "name", "imageUrl", "imageType")), 0,
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), 0, 0, 0L, 0, false);
    when(mockGoodRepo.findAll(PageRequest.of(0, 1)))
        .thenReturn(new PageImpl<>(Collections.emptyList()));

    // Run the test
    final GoodResponse result = goodServiceImplUnderTest.getAllGoods(0, 0);

    // Verify the results
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void testFindByGoodName() {
    // Setup
    // Configure GoodRepo.findByGoodsName(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
            List.of()));
    when(mockGoodRepo.findByGoodsName("name")).thenReturn(good);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.findByGoodName("name");

    // Verify the results
  }

  @Test
  void testGetGoodById() {
    // Setup
    // Configure GoodRepo.findByGoodsId(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
            List.of()));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.getGoodById(0);

    // Verify the results
  }

  @Test
  void testCreateGood() {
    // Setup
    final GoodDto goodDto = new GoodDto(0, "goodsName", 0,
        List.of(new ImageDto(0, "name", "imageUrl", "imageType")), 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final MultipartFile[] files = new MultipartFile[]{};

    // Configure Cloudinary.uploader(...).
    final Uploader uploader = new Uploader(new Cloudinary(Map.ofEntries()), null);
    when(mockCloudinary.uploader()).thenReturn(uploader);

    // Configure ImageRepo.saveAll(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final List<Image> images = List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
                List.of()))));
    when(mockImageRepo.saveAll(List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), new UserStatist(),
                List.of())))))).thenReturn(images);

    // Configure AccountRepo.findByAccountId(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account2 = new Account();
    account2.setAccountId(0);
    account2.setEmail("email");
    account2.setUsername("username");
    account2.setPassword("password");
    account2.setFullName("fullName");
    account2.setAddress("address");
    account2.setAge(0);
    account2.setPhone("phone");
    account2.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account2.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account2.setIs_deleted(false);
    final Role role1 = new Role();
    account2.setRoles(List.of(role1));
    userStatist1.setAccount(account2);
    final Account account1 = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist1, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Image(0, "name", "fileType", "imageUrl",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)), null)));
    when(mockAccountRepo.findByAccountId(0)).thenReturn(account1);

    // Configure GoodRepo.save(...).
    final UserStatist userStatist2 = new UserStatist();
    userStatist2.setStaticId(0);
    userStatist2.setCount(0);
    final Account account3 = new Account();
    account3.setAccountId(0);
    account3.setEmail("email");
    account3.setUsername("username");
    account3.setPassword("password");
    account3.setFullName("fullName");
    account3.setAddress("address");
    account3.setAge(0);
    account3.setPhone("phone");
    account3.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setIs_deleted(false);
    final Role role2 = new Role();
    account3.setRoles(List.of(role2));
    userStatist2.setAccount(account3);
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist2,
            List.of()));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.createGood(goodDto, files);

    // Verify the results
    verify(mockImageRepo).saveAll(List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), new UserStatist(),
                List.of())))));
  }

  @Test
  void testUpdateGood() throws Exception {
    // Setup
    final GoodDto goodDto = new GoodDto(0, "goodsName", 0,
        List.of(new ImageDto(0, "name", "imageUrl", "imageType")), 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final MultipartFile[] files = new MultipartFile[]{};

    // Configure GoodRepo.findByGoodsId(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
            List.of()));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Configure Cloudinary.uploader(...).
    final Uploader uploader = new Uploader(new Cloudinary(Map.ofEntries()), null);
    when(mockCloudinary.uploader()).thenReturn(uploader);

    // Configure ImageRepo.saveAll(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role1 = new Role();
    account1.setRoles(List.of(role1));
    userStatist1.setAccount(account1);
    final List<Image> images = List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist1,
                List.of()))));
    when(mockImageRepo.saveAll(List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), new UserStatist(),
                List.of())))))).thenReturn(images);

    // Configure AccountRepo.findByAccountId(...).
    final UserStatist userStatist2 = new UserStatist();
    userStatist2.setStaticId(0);
    userStatist2.setCount(0);
    final Account account3 = new Account();
    account3.setAccountId(0);
    account3.setEmail("email");
    account3.setUsername("username");
    account3.setPassword("password");
    account3.setFullName("fullName");
    account3.setAddress("address");
    account3.setAge(0);
    account3.setPhone("phone");
    account3.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setIs_deleted(false);
    final Role role2 = new Role();
    account3.setRoles(List.of(role2));
    userStatist2.setAccount(account3);
    final Account account2 = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist2, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Image(0, "name", "fileType", "imageUrl",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)), null)));
    when(mockAccountRepo.findByAccountId(0)).thenReturn(account2);

    // Configure GoodRepo.save(...).
    final UserStatist userStatist3 = new UserStatist();
    userStatist3.setStaticId(0);
    userStatist3.setCount(0);
    final Account account4 = new Account();
    account4.setAccountId(0);
    account4.setEmail("email");
    account4.setUsername("username");
    account4.setPassword("password");
    account4.setFullName("fullName");
    account4.setAddress("address");
    account4.setAge(0);
    account4.setPhone("phone");
    account4.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account4.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account4.setIs_deleted(false);
    final Role role3 = new Role();
    account4.setRoles(List.of(role3));
    userStatist3.setAccount(account4);
    final Good good1 = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist3,
            List.of()));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good1);

    // Run the test
    final GoodDto result = goodServiceImplUnderTest.updateGood(goodDto, 0, files);

    // Verify the results
    verify(mockImageRepo).deleteImagesByGoods(0);
    verify(mockImageRepo).saveAll(List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), new UserStatist(),
                List.of())))));
  }

  @Test
  void testUpdateGood_ThrowsIOException() {
    // Setup
    final GoodDto goodDto = new GoodDto(0, "goodsName", 0,
        List.of(new ImageDto(0, "name", "imageUrl", "imageType")), 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    final MultipartFile[] files = new MultipartFile[]{};

    // Configure GoodRepo.findByGoodsId(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
            List.of()));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Configure Cloudinary.uploader(...).
    final Uploader uploader = new Uploader(new Cloudinary(Map.ofEntries()), null);
    when(mockCloudinary.uploader()).thenReturn(uploader);

    // Configure ImageRepo.saveAll(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role1 = new Role();
    account1.setRoles(List.of(role1));
    userStatist1.setAccount(account1);
    final List<Image> images = List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist1,
                List.of()))));
    when(mockImageRepo.saveAll(List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), new UserStatist(),
                List.of())))))).thenReturn(images);

    // Configure AccountRepo.findByAccountId(...).
    final UserStatist userStatist2 = new UserStatist();
    userStatist2.setStaticId(0);
    userStatist2.setCount(0);
    final Account account3 = new Account();
    account3.setAccountId(0);
    account3.setEmail("email");
    account3.setUsername("username");
    account3.setPassword("password");
    account3.setFullName("fullName");
    account3.setAddress("address");
    account3.setAge(0);
    account3.setPhone("phone");
    account3.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account3.setIs_deleted(false);
    final Role role2 = new Role();
    account3.setRoles(List.of(role2));
    userStatist2.setAccount(account3);
    final Account account2 = new Account(0, "email", "username", "password", "fullName", "address",
        0, "phone", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist2, List.of(
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Image(0, "name", "fileType", "imageUrl",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)), null)));
    when(mockAccountRepo.findByAccountId(0)).thenReturn(account2);

    // Configure GoodRepo.save(...).
    final UserStatist userStatist3 = new UserStatist();
    userStatist3.setStaticId(0);
    userStatist3.setCount(0);
    final Account account4 = new Account();
    account4.setAccountId(0);
    account4.setEmail("email");
    account4.setUsername("username");
    account4.setPassword("password");
    account4.setFullName("fullName");
    account4.setAddress("address");
    account4.setAge(0);
    account4.setPhone("phone");
    account4.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account4.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account4.setIs_deleted(false);
    final Role role3 = new Role();
    account4.setRoles(List.of(role3));
    userStatist3.setAccount(account4);
    final Good good1 = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist3,
            List.of()));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good1);

    // Run the test
    assertThatThrownBy(() -> goodServiceImplUnderTest.updateGood(goodDto, 0, files))
        .isInstanceOf(IOException.class);
    verify(mockImageRepo).deleteImagesByGoods(0);
    verify(mockImageRepo).saveAll(List.of(new Image(0, "name", "fileType", "imageUrl",
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new Good(0, "goodsName", 0, new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(),
            new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
                new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                    new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), new UserStatist(),
                List.of())))));
  }

  @Test
  void testDeleteGood() {
    // Setup
    // Configure GoodRepo.findByGoodsId(...).
    final UserStatist userStatist = new UserStatist();
    userStatist.setStaticId(0);
    userStatist.setCount(0);
    final Account account = new Account();
    account.setAccountId(0);
    account.setEmail("email");
    account.setUsername("username");
    account.setPassword("password");
    account.setFullName("fullName");
    account.setAddress("address");
    account.setAge(0);
    account.setPhone("phone");
    account.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account.setIs_deleted(false);
    final Role role = new Role();
    account.setRoles(List.of(role));
    userStatist.setAccount(account);
    final Good good = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist,
            List.of()));
    when(mockGoodRepo.findByGoodsId(0)).thenReturn(good);

    // Configure GoodRepo.save(...).
    final UserStatist userStatist1 = new UserStatist();
    userStatist1.setStaticId(0);
    userStatist1.setCount(0);
    final Account account1 = new Account();
    account1.setAccountId(0);
    account1.setEmail("email");
    account1.setUsername("username");
    account1.setPassword("password");
    account1.setFullName("fullName");
    account1.setAddress("address");
    account1.setAge(0);
    account1.setPhone("phone");
    account1.setCreateAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setUpdatedAt(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    account1.setIs_deleted(false);
    final Role role1 = new Role();
    account1.setRoles(List.of(role1));
    userStatist1.setAccount(account1);
    final Good good1 = new Good(0, "goodsName", 0,
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
        new Image(0, "name", "fileType", "imageUrl",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), null)),
        new Account(0, "email", "username", "password", "fullName", "address", 0, "phone",
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
            new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), false, List.of(
            new Role(0, "roleName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())), userStatist1,
            List.of()));
    when(mockGoodRepo.save(any(Good.class))).thenReturn(good1);

    // Run the test
    goodServiceImplUnderTest.deleteGood(0);

    // Verify the results
    verify(mockGoodRepo).save(any(Good.class));
  }

  @Test
  void testUpload() throws Exception {
    // Setup
    final MultipartFile multipartFile = null;

    // Configure Cloudinary.uploader(...).
    final Uploader uploader = new Uploader(new Cloudinary(Map.ofEntries()), null);
    when(mockCloudinary.uploader()).thenReturn(uploader);

    // Run the test
    final Map result = goodServiceImplUnderTest.upload(multipartFile);

    // Verify the results
  }

  @Test
  void testUpload_ThrowsIOException() {
    // Setup
    final MultipartFile multipartFile = null;

    // Configure Cloudinary.uploader(...).
    final Uploader uploader = new Uploader(new Cloudinary(Map.ofEntries()), null);
    when(mockCloudinary.uploader()).thenReturn(uploader);

    // Run the test
    assertThatThrownBy(() -> goodServiceImplUnderTest.upload(multipartFile))
        .isInstanceOf(IOException.class);
  }
}
