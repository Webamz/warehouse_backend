package com.warehousebackend.impl;

import com.warehousebackend.model.repostiory.TestRepository;
import com.warehousebackend.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TestServiceImplTest {
    private final TestRepository mockTestRepository = Mockito.mock(TestRepository.class);
    private final TestService mockTestService = Mockito.mock(TestService.class);

    @Test
    void save_test_results_should_work() {
        com.warehousebackend.model.entities.Test test = new com.warehousebackend.model.entities.Test();
        when(mockTestRepository.save(Mockito.any(com.warehousebackend.model.entities.Test.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        mockTestService.saveTestResults(test);

        assertNotNull(mockTestRepository.findById(1L));
    }
}