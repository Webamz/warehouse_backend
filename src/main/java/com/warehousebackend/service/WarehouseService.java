package com.warehousebackend.service;


import com.warehousebackend.model.entities.AppClient;
import com.warehousebackend.model.entities.Warehouse;

import java.util.List;
import java.util.Set;

public interface WarehouseService {
    Warehouse findTheWarehouseById(Long id);

    void saveUpdatedWarehouse(Warehouse warehouse) throws Exception;

    boolean deleteWarehouse(long id) throws Exception;

    Set<Warehouse> findWarehouseMatches(String username);

    boolean saveWarehouseForClient(Warehouse warehouse, String username);

    boolean removeWarehouseForClient(Warehouse warehouse, String username);

    boolean isWarehouseSaved(Long warehouseId, String username);

    List<Warehouse> findSavedTheWarehouses(AppClient appClient);

    Set<Warehouse> getAllTheWarehousesForBusiness(String username);
    
    Set<Warehouse> getAllTheWarehouseMatchesForClient(String username);

    void createWarehouse(Warehouse offer);
}
