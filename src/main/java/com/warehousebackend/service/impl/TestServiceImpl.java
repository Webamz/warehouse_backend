package com.warehousebackend.service.impl;

import com.warehousebackend.model.entities.AppClient;
import com.warehousebackend.model.entities.Test;
import com.warehousebackend.model.entities.Warehouse;
import com.warehousebackend.model.repostiory.TestRepository;
import com.warehousebackend.service.TestService;
import com.warehousebackend.service.UserService;
import com.warehousebackend.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final UserService userService;
    private final WarehouseService warehouseService;


    @Autowired
    public TestServiceImpl(TestRepository testRepository, UserService userService, WarehouseService warehouseService) {
        this.testRepository = testRepository;
        this.userService = userService;
        this.warehouseService = warehouseService;
    }

    @Override
    public void saveTestResults(Test results) {
        AppClient currentUserAppClient = this.userService.findAppClientByUsername(results.getUsername());
        if (currentUserAppClient.getTestResults() != null) {
            results.setId(currentUserAppClient.getTestResults().getId());
        }
        this.testRepository.save(results);
        currentUserAppClient.setTestResults(results);

        Set<Warehouse> warehouseMatches = this.warehouseService.findWarehouseMatches(currentUserAppClient.getUsername());
        currentUserAppClient.setWarehouse_matches(warehouseMatches);
        this.userService.saveUpdatedUserClient(currentUserAppClient);
    }
}
