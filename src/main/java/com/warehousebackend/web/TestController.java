package com.warehousebackend.web;


import com.warehousebackend.model.entities.Test;
import com.warehousebackend.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }
    
    @PostMapping("/test")
    @Operation(summary = "Save test results", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<HttpStatus> saveTestResults(@RequestBody Test results) {
        this.testService.saveTestResults(results);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
