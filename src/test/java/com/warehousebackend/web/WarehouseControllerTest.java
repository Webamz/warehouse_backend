package com.warehousebackend.web;

import com.warehousebackend.model.dto.WarehouseInfoDto;
import com.warehousebackend.model.dto.WarehouseInfoUpdateDto;
import com.warehousebackend.model.entities.AppClient;
import com.warehousebackend.model.entities.Category;
import com.warehousebackend.model.entities.Location;
import com.warehousebackend.model.entities.Warehouse;
import com.warehousebackend.model.entities.enums.CategoryNameEnum;
import com.warehousebackend.model.entities.enums.LocationEnum;
import com.warehousebackend.service.UserService;
import com.warehousebackend.service.WarehouseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseControllerTest extends AbstractTest {
    @Autowired
    private WarehouseController controller;
    private WarehouseInfoDto warehouseInfoDto;
    private WarehouseInfoUpdateDto warehouseInfoUpdateDto;
    private Warehouse warehouse;

    @Before
    public void setUp() {
        // prepare data
        warehouseInfoDto = new WarehouseInfoDto();
        warehouseInfoDto.setCreator("user");
        warehouseInfoDto.setDescription("What is Equestrian Tourism?\n" +
                "The Equestrian Tourism is an activity that combines the passion for horse riding with the interest to visit different regions, provinces and countries, which allows to discover different cultures, other people and typical gastronomy.\n" +
                "\n" +
                "Is it difficult to ride a horse?\n" +
                "The practice of horse riding is known as equitation and it is relatively simple to start with, at least to be able to go on rides in the countryside. If your interest grows further, you will want to improve your riding skills and acquire more knowledge about horses.\n" +
                "\n" +
                "How fast your skills improve depend on every individual and on your purpose, you can ride for pleasure in the nature in all types of terrain or choose an equestrian sport and train professionally.\n" +
                "\n");
        warehouseInfoDto.setIntro("What is Equestrian Tourism?\n" +
                "The Equestrian Tourism is an activity that combines the passion for horse riding with the interest to visit different regions, provinces and countries, which allows to discover different cultures, other people and typical gastronomy.\n" +
                "\n" +
                "Is it difficult to ride a horse?\n" +
                "The practice of horse riding is known as equitation and it is relatively simple to start with, at least to be able to go on rides in the countryside. If your interest grows further, you will want to improve your riding skills and acquire more knowledge about horses.\n" +
                "\n" +
                "How fast your skills improve depend on every individual and on your purpose, you can ride for pleasure in the nature in all types of terrain or choose an equestrian sport and train professionally.\n" +
                "\n");
        warehouseInfoDto.setSlogan("slogan");
        warehouseInfoDto.setProfileImgUrl("url");
        warehouseInfoDto.setGalleryImgUrl1("url");
        warehouseInfoDto.setGalleryImgUrl2("url");
        warehouseInfoDto.setGalleryImgUrl3("url");
        warehouseInfoDto.setProfileImg_id("id");
        warehouseInfoDto.setGalleryImg1_id("id");
        warehouseInfoDto.setGalleryImg2_id("id");
        warehouseInfoDto.setGalleryImg3_id("id");
        warehouseInfoDto.setPrice(BigDecimal.valueOf(123));
        warehouseInfoDto.setContactInfo("How fast your skills improve depend on every individual and on your purpose.");


        warehouseInfoUpdateDto = new WarehouseInfoUpdateDto();
        warehouseInfoUpdateDto.setCreator("user");
        warehouseInfoUpdateDto.setCategory(CategoryNameEnum.ACTIVE);
        warehouseInfoUpdateDto.setLocation(LocationEnum.KIGALI);
        warehouseInfoUpdateDto.setDescription("What is Equestrian Tourism?\n" +
                "The Equestrian Tourism is an activity that combines the passion for horse riding with the interest to visit different regions, provinces and countries, which allows to discover different cultures, other people and typical gastronomy.\n" +
                "\n" +
                "Is it difficult to ride a horse?\n" +
                "The practice of horse riding is known as equitation and it is relatively simple to start with, at least to be able to go on rides in the countryside. If your interest grows further, you will want to improve your riding skills and acquire more knowledge about horses.\n" +
                "\n" +
                "How fast your skills improve depend on every individual and on your purpose, you can ride for pleasure in the nature in all types of terrain or choose an equestrian sport and train professionally.\n" +
                "\n");
        warehouseInfoUpdateDto.setIntro("What is Equestrian Tourism?\n" +
                "The Equestrian Tourism is an activity that combines the passion for horse riding with the interest to visit different regions, provinces and countries, which allows to discover different cultures, other people and typical gastronomy.\n" +
                "\n" +
                "Is it difficult to ride a horse?\n" +
                "The practice of horse riding is known as equitation and it is relatively simple to start with, at least to be able to go on rides in the countryside. If your interest grows further, you will want to improve your riding skills and acquire more knowledge about horses.\n" +
                "\n" +
                "How fast your skills improve depend on every individual and on your purpose, you can ride for pleasure in the nature in all types of terrain or choose an equestrian sport and train professionally.\n" +
                "\n");
        warehouseInfoUpdateDto.setId(1L);
        warehouseInfoUpdateDto.setSlogan("slogan");
        warehouseInfoUpdateDto.setProfileImgUrl("url");
        warehouseInfoUpdateDto.setGalleryImgUrl1("url");
        warehouseInfoUpdateDto.setGalleryImgUrl2("url");
        warehouseInfoUpdateDto.setGalleryImgUrl3("url");
        warehouseInfoUpdateDto.setProfileImg_id("id");
        warehouseInfoUpdateDto.setGalleryImg1_id("id");
        warehouseInfoUpdateDto.setGalleryImg2_id("id");
        warehouseInfoUpdateDto.setGalleryImg3_id("id");
        warehouseInfoUpdateDto.setPrice(BigDecimal.valueOf(123));
        warehouseInfoUpdateDto.setContactInfo("How fast your skills improve depend on every individual and on your purpose.");

        ModelMapper modelMapper = new ModelMapper();
        Category category = new Category(CategoryNameEnum.ACTIVE);
        Location location = new Location(LocationEnum.KIGALI);
        warehouse = modelMapper.map(warehouseInfoDto, Warehouse.class);
        warehouse.setCategory(category);
        warehouse.setLocation(location);
        AppClient client = new AppClient();
        client.setUsername("user");
        String username = "user";
        List<Warehouse> theWarehouses = new ArrayList<>();
        warehouse = new Warehouse();
        theWarehouses.add(warehouse);

        super.setUp();
    }

    @MockBean
    private WarehouseService service;
    @MockBean
    private UserService userService;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void create_offer_should_work() throws Exception {
        String uri = "/theWarehouses";
        String inputJson = super.mapToJson(warehouseInfoDto);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void warehouse_details_should_work() throws Exception {
        String uri = "/theWarehouses/1";
        Long id = 1L;
        String inputJson = super.mapToJson(id);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        when(service.findTheWarehouseById(id)).thenReturn(warehouse);

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void delete_warehouse_should_work() throws Exception {
        String uri = "/theWarehouses/1";
        long id = 1L;
        when(service.deleteWarehouse(id)).thenReturn(true);
        String inputJson = super.mapToJson(id);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void delete_warehouse_should_work_when_not_found() throws Exception {
        String uri = "/theWarehouses/1";
        long id = 1L;
        when(service.deleteWarehouse(id)).thenReturn(false);

        String inputJson = super.mapToJson(id);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void update_warehouse_should_work() throws Exception {
        String uri = "/theWarehouses";
        String inputJson = super.mapToJson(warehouseInfoUpdateDto);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
    }
}
