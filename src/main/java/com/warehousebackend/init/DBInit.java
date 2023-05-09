package com.warehousebackend.init;


import com.warehousebackend.service.CategoryService;
import com.warehousebackend.service.LocationService;
import com.warehousebackend.service.UserService;
import com.warehousebackend.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final UserService userService;
    private final CategoryService categoryService;
    private final WarehouseService warehouseService;
    private final LocationService locationService;

    @Autowired
    public DBInit(UserService userService, CategoryService categoryService, WarehouseService warehouseService, LocationService locationService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.warehouseService = warehouseService;
        this.locationService = locationService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsersAndUserRoles();
        this.categoryService.initCategories();
        this.locationService.initLocations();
    }

}
