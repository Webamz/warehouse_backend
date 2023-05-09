package com.warehousebackend.model.repostiory;


import com.warehousebackend.model.entities.Location;
import com.warehousebackend.model.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Set<Warehouse> findAllByCreator(String creator);

    List<Warehouse> findAllByLocation(Location location);
}
