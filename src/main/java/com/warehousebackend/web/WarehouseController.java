package com.warehousebackend.web;

import com.warehousebackend.model.dto.WarehouseInfoDto;
import com.warehousebackend.model.dto.WarehouseInfoUpdateDto;
import com.warehousebackend.model.entities.*;
import com.warehousebackend.service.CategoryService;
import com.warehousebackend.service.LocationService;
import com.warehousebackend.service.UserService;
import com.warehousebackend.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/theWarehouses")
@CrossOrigin(origins = "http://localhost:3000")
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public WarehouseController(WarehouseService warehouseService, CategoryService categoryService, LocationService locationService, UserService userService, ModelMapper modelMapper) {
        this.warehouseService = warehouseService;
        this.categoryService = categoryService;
        this.locationService = locationService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Operation(summary = "Create new warehouse", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<HttpStatus> saveWarehouse(@RequestBody WarehouseInfoDto info) {
        Warehouse offer = this.modelMapper.map(info, Warehouse.class);
        Category category = this.categoryService.findByName(info.getCategory());
        Location location = this.locationService.getLocationByName(info.getLocation());
        offer.setLocation(location);
        offer.setCategory(category);
        BusinessOwner business = this.userService.findBusinessByUsername(info.getCreator());
        Set<Warehouse> warehouse_offers = business.getWarehouse_offers();
        warehouse_offers.add(offer);
        business.setWarehouse_offers(warehouse_offers);
        this.warehouseService.createWarehouse(offer);
        this.userService.saveUpdatedUser(business);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/is-saved")
    @Operation(summary = "Show if warehouse is saved in favorites", security = @SecurityRequirement(name = "bearerAuth"))
    public boolean isWarehouseSaved(@RequestParam Long id, @RequestParam String username) {
        return this.warehouseService.isWarehouseSaved(id, username);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Show warehouse details", security = @SecurityRequirement(name = "bearerAuth"))
    public Warehouse getWarehouseDetails(@PathVariable Long id) {
        return this.warehouseService.findTheWarehouseById(id);
    }


    @PostMapping("/save")
    @Operation(summary = "Save warehouse in favorites", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Long> save(@RequestParam Long id, @RequestParam String username) {
        Warehouse warehouse = this.warehouseService.findTheWarehouseById(id);
        boolean isSaved = this.warehouseService.saveWarehouseForClient(warehouse, username);
        if (!isSaved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove warehouse from favorites", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Long> removeWarehouse(@RequestParam Long id, @RequestParam String username) {
        Warehouse warehouse = this.warehouseService.findTheWarehouseById(id);
        boolean isRemoved = this.warehouseService.removeWarehouseForClient(warehouse, username);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PutMapping
    @Operation(summary = "Update warehouse,(use existing warehouse id)", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> updateWarehouse(@RequestBody WarehouseInfoUpdateDto info) throws Exception {
        Warehouse offer = this.modelMapper.map(info, Warehouse.class);
        Category category = this.categoryService.findByName(info.getCategory());
        Location location = this.locationService.getLocationByName(info.getLocation());
        offer.setLocation(location);
        offer.setCategory(category);
        this.warehouseService.saveUpdatedWarehouse(offer);
        return new ResponseEntity<Warehouse>(offer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete warehouse", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Long> deleteWarehouse(@PathVariable Long id) throws Exception {
        boolean isRemoved = this.warehouseService.deleteWarehouse(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/saved")
    @Operation(summary = "Show theWarehouses that are saved in favorites", security = @SecurityRequirement(name = "bearerAuth"))
    public List<Warehouse> savedTheWarehouses(@RequestParam String username) {
        AppClient appClient = this.userService.findAppClientByUsername(username);
        return this.warehouseService.findSavedTheWarehouses(appClient);

    }
}

