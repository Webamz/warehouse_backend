package com.warehousebackend.web;

import com.warehousebackend.model.entities.Warehouse;
import com.warehousebackend.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(origins = "https://frontend-warehouse.onrender.com/")
public class HomeController {
    private final WarehouseService warehouseService;

    @Autowired
    public HomeController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/home")
    @Operation(summary = "Show client/business homepage", security = @SecurityRequirement(name = "bearerAuth"))
    public Set<Warehouse> theWarehousesShow(@RequestParam String username, @RequestParam String role) {
        if (role.equals("user")) {
            return this.warehouseService.getAllTheWarehouseMatchesForClient(username);
        }
        return this.warehouseService.getAllTheWarehousesForBusiness(username);
    }
}
