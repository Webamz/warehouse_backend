package com.warehousebackend.impl;


import com.cloudinary.Cloudinary;
import com.warehousebackend.handler.NotFoundException;
import com.warehousebackend.model.dto.WarehouseInfoDto;
import com.warehousebackend.model.entities.AppClient;
import com.warehousebackend.model.entities.Category;
import com.warehousebackend.model.entities.Location;
import com.warehousebackend.model.entities.Warehouse;
import com.warehousebackend.model.entities.enums.CategoryNameEnum;
import com.warehousebackend.model.entities.enums.LocationEnum;
import com.warehousebackend.model.repostiory.CategoryRepository;
import com.warehousebackend.model.repostiory.WarehouseRepository;
import com.warehousebackend.service.*;
import com.warehousebackend.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

class WarehouseServiceImplTest {
    private WarehouseRepository mockWarehouseRepository;
    private NotificationService notificationService;
    private WarehouseService warehouseServiceToTest;
    private AppClient appClient;
    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        mockWarehouseRepository = mock(WarehouseRepository.class);
        CategoryRepository mockCategoryRepository;
        CategoryService categoryServiceTest = mock(CategoryService.class);
        LocationService locationServiceTest = mock(LocationService.class);
        mockCategoryRepository = mock(CategoryRepository.class);
        appClient = new AppClient();
        Cloudinary cloudinary = mock(Cloudinary.class);
        UserService userServiceTest = mock(UserService.class);

        warehouseServiceToTest = new WarehouseServiceImpl
                (mockWarehouseRepository, categoryServiceTest, userServiceTest, locationServiceTest, cloudinary);

        // prepare warehouse data
        warehouse = new Warehouse();
        warehouse.setId(1L);
        WarehouseInfoDto warehouseServiceModel = new WarehouseInfoDto();
        warehouseServiceModel.setCategory(CategoryNameEnum.ACTIVE);
        warehouseServiceModel.setLocation(LocationEnum.KIGALI);
        warehouse.setIntro("intro");
        warehouse.setSlogan("slogan");
        warehouse.setDescription("description");
        warehouse.setProfileImgUrl("img_url");
        warehouse.setGalleryImgUrl1("img_1");
        warehouse.setGalleryImgUrl2("img_2");
        warehouse.setGalleryImgUrl3("img_3");
        warehouse.setProfileImg_id("0");
        warehouse.setGalleryImg1_id("1");
        warehouse.setGalleryImg2_id("2");
        warehouse.setGalleryImg3_id("3");

        Location location = new Location();
        location.setName(LocationEnum.KIGALI);
        warehouse.setLocation(location);
        warehouse.setCreator("businessOwner");
        warehouse.setPrice(new BigDecimal("100"));
        Category category = new Category();
        category.setName(CategoryNameEnum.ACTIVE);
        warehouse.setCategory(category);
        warehouse.setName("warehouseName");
        warehouse.setContactInfo("contact info");

        //config mock
        when(mockCategoryRepository.save(Mockito.any(Category.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(mockWarehouseRepository.save(Mockito.any(Warehouse.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(categoryServiceTest.findByName(CategoryNameEnum.ACTIVE)).
                thenReturn(new Category() {{
                    setName(CategoryNameEnum.ACTIVE);
                }});

        when(locationServiceTest.getLocationByName(LocationEnum.KIGALI)).
                thenReturn(location);
        when(mockWarehouseRepository.findById(1L)).
                thenReturn(Optional.of(warehouse));
        when(userServiceTest.findAppClientByUsername("username")).
                thenReturn(appClient);

    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(
                NotFoundException.class, () -> {
                    warehouseServiceToTest.findTheWarehouseById(null);
                    warehouseServiceToTest.deleteWarehouse(0L);
                }
        );
    }

    @Test
    void findWarehouseById_should_Work() {
        Assertions.assertEquals(warehouse, warehouseServiceToTest.findTheWarehouseById(1L));
    }

    @Test
    void saveWarehouseForClient_should_work() {
        appClient.setSaved_theWarehouses(new ArrayList<>());
        warehouseServiceToTest.saveWarehouseForClient(warehouse, "username");
        assertEquals(1, appClient.getSaved_theWarehouses().size());
    }

    @Test
    void removeWarehouseForClient_should_work() {
        List<Warehouse> saved = new ArrayList<>();
        saved.add(warehouse);
        appClient.setSaved_theWarehouses(saved);
        warehouseServiceToTest.removeWarehouseForClient(warehouse, "username");
        assertEquals(0, appClient.getSaved_theWarehouses().size());
    }

    @Test
    void isWarehouseSaved_should_work() {
        appClient.setSaved_theWarehouses(new ArrayList<>());
        warehouseServiceToTest.saveWarehouseForClient(warehouse, "username");
        assertTrue(warehouseServiceToTest.isWarehouseSaved(1L, "username"));
    }

    @Test
    void findSavedTheWarehouses_should_work() {
        appClient.setSaved_theWarehouses(new ArrayList<>());
        warehouseServiceToTest.saveWarehouseForClient(warehouse, "username");
        assertEquals(1, warehouseServiceToTest.findSavedTheWarehouses(appClient).size());
    }
}