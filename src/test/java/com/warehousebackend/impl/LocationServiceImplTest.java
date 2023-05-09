package com.warehousebackend.impl;

import com.warehousebackend.handler.NotFoundException;
import com.warehousebackend.model.entities.Location;
import com.warehousebackend.model.entities.enums.LocationEnum;
import com.warehousebackend.model.repostiory.LocationRepository;
import com.warehousebackend.service.LocationService;
import com.warehousebackend.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.testng.AssertJUnit.assertEquals;

class LocationServiceImplTest {
    private LocationRepository mockLocationRepository;
    private LocationService locationServiceToTest;
    private Location location;

    @BeforeEach
    public void setUp() {
        mockLocationRepository = mock(LocationRepository.class);
        locationServiceToTest = new LocationServiceImpl(mockLocationRepository);
        location = new Location();
        location.setName(LocationEnum.KIGALI);
    }

    @Test
    void getLocationByName_should_work() {
        Mockito.when(mockLocationRepository.findByName(LocationEnum.KIGALI)).
                thenReturn(Optional.of(location));
        Location locationByName = locationServiceToTest.getLocationByName(LocationEnum.KIGALI);

        assertEquals(location.getName(), locationByName.getName());
    }

    @Test
    void testLocationNotFound() {
        Assertions.assertThrows(
                NotFoundException.class,
                () -> locationServiceToTest.getLocationByName(LocationEnum.KIGALI));
    }

    @Test
    void initLocations_should_work() {
        locationServiceToTest.initLocations();
        assertEquals(4, locationServiceToTest.initLocations().size());
    }
}