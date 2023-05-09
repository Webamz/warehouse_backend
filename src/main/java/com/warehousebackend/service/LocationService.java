package com.warehousebackend.service;


import com.warehousebackend.model.entities.Location;
import com.warehousebackend.model.entities.enums.LocationEnum;

import java.util.List;

public interface LocationService {
    List<Location> initLocations();

    Location getLocationByName(LocationEnum locationEnum);
}
