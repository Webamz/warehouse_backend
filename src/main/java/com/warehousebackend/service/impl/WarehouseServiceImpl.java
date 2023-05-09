package com.warehousebackend.service.impl;


import com.cloudinary.Cloudinary;
import com.warehousebackend.handler.NotFoundException;
import com.warehousebackend.model.entities.AppClient;
import com.warehousebackend.model.entities.BusinessOwner;
import com.warehousebackend.model.entities.Location;
import com.warehousebackend.model.entities.Warehouse;
import com.warehousebackend.model.entities.enums.CategoryNameEnum;
import com.warehousebackend.model.entities.enums.LocationEnum;
import com.warehousebackend.model.repostiory.WarehouseRepository;
import com.warehousebackend.service.CategoryService;
import com.warehousebackend.service.LocationService;
import com.warehousebackend.service.UserService;
import com.warehousebackend.service.WarehouseService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final LocationService locationService;
    private final Cloudinary cloudinary;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository, CategoryService categoryService, UserService userService, LocationService locationService, Cloudinary cloudinary) {
        this.warehouseRepository = warehouseRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.locationService = locationService;
        this.cloudinary = cloudinary;
    }



    @Override
    public Warehouse findTheWarehouseById(Long id) {
        Optional<Warehouse> warehouse = this.warehouseRepository.findById(id);
        if (warehouse.isPresent()) {
            return warehouse.get();
        } else {
            throw new NotFoundException("This warehouse does not exist");
        }
    }

    @SneakyThrows
    @Override
    public void saveUpdatedWarehouse(Warehouse warehouse) {
        Optional<Warehouse> byId = this.warehouseRepository.findById(warehouse.getId());
        if (byId.isPresent()) {
            deleteResourcesById(byId.get());
        }
        this.warehouseRepository.save(warehouse);
    }

    @Override
    public boolean deleteWarehouse(long id) throws Exception {
        Optional<Warehouse> byId = this.warehouseRepository.findById(id);
        if (byId.isPresent()) {
            deleteResourcesById(byId.get());
            BusinessOwner business = this.userService.findBusinessByUsername(byId.get().getCreator());
            business.getWarehouse_offers().remove(byId.get());
            this.userService.findAndRemoveWarehouseFromClientsRecords(byId.get());
            this.warehouseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void deleteResourcesById(Warehouse byId) throws Exception {
        String profileImgId = byId.getProfileImg_id();
        String galleryImgId1 = byId.getGalleryImg1_id();
        String galleryImgId2 = byId.getGalleryImg2_id();
        String galleryImgId3 = byId.getGalleryImg3_id();

        cloudinary.api().deleteResources(Arrays.asList(profileImgId, galleryImgId1, galleryImgId2, galleryImgId3),
                Map.of("invalidate", true));
    }


    @Override
    public Set<Warehouse> findWarehouseMatches(String username) {
        AppClient currentUserAppClient = this.userService.findAppClientByUsername(username);
        Set<Warehouse> warehouse_matches = new HashSet<>();
        if (currentUserAppClient.getTestResults() != null) {
            boolean isAdded = false;
            Random rand = new Random();
            LocationEnum location = currentUserAppClient.getTestResults().getLocation();
            Location locationByName = this.locationService.getLocationByName(location);
            List<Warehouse> allByLocation = this.warehouseRepository.findAllByLocation(locationByName);
            List<CategoryNameEnum> testCategoryResults = new ArrayList<>();

            testCategoryResults.add(currentUserAppClient.getTestResults().getCategoryOne());
            testCategoryResults.add(currentUserAppClient.getTestResults().getCategoryTwo());
            testCategoryResults.add(currentUserAppClient.getTestResults().getCategoryThree());
            testCategoryResults.add(currentUserAppClient.getTestResults().getCategoryFour());
            testCategoryResults.add(currentUserAppClient.getTestResults().getCategoryFive());
            testCategoryResults.add(currentUserAppClient.getTestResults().getCategorySix());

            if (allByLocation.size() > 0) {

                for (int i = 0; i < 10; i++) {
                    int randomIndex = rand.nextInt(allByLocation.size());
                    Warehouse randomWarehouse = allByLocation.get(randomIndex);
                    if (warehouse_matches.contains(randomWarehouse)) {
                        continue;
                    }
                    for (CategoryNameEnum testCategory : testCategoryResults) {
                        if (randomWarehouse.getCategory().getName().equals(testCategory)) {
                            warehouse_matches.add(randomWarehouse);
                            isAdded = true;
                        }
                        if (isAdded) {
                            isAdded = false;
                            break;
                        }
                    }
                }
            }
        }
        return warehouse_matches;
    }

    @Override
    public boolean saveWarehouseForClient(Warehouse warehouse, String username) {
        AppClient currentUserAppClient = this.userService.findAppClientByUsername(username);
        Optional<Warehouse> warehouseById = this.warehouseRepository.findById(warehouse.getId());
        List<Warehouse> saved_theWarehouses = currentUserAppClient.getSaved_theWarehouses();
        if (warehouseById.isPresent() && !(saved_theWarehouses.contains(warehouseById.get()))) {
            saved_theWarehouses.add(warehouseById.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeWarehouseForClient(Warehouse warehouse, String username) {
        AppClient currentUserAppClient = this.userService.findAppClientByUsername(username);
        Optional<Warehouse> warehouseById = this.warehouseRepository.findById(warehouse.getId());
        if (currentUserAppClient != null) {
            warehouseById.ifPresent(value -> currentUserAppClient.getSaved_theWarehouses().remove(value));
            return true;
        }
        return false;
    }

    @Override
    public boolean isWarehouseSaved(Long warehouseId, String username) {
        Optional<Warehouse> byId = this.warehouseRepository.findById(warehouseId);
        if (byId.isPresent()) {
            AppClient currentUserAppClient = this.userService.findAppClientByUsername(username);
            return currentUserAppClient.getSaved_theWarehouses().contains(byId.get());
        }
        return false;
    }

    @Override
    public List<Warehouse> findSavedTheWarehouses(AppClient currentAppClient) {
        return currentAppClient.getSaved_theWarehouses();
    }

    @Override
    public Set<Warehouse> getAllTheWarehousesForBusiness(String username) {
        return this.warehouseRepository.findAllByCreator(username);
    }

    @Override
    public Set<Warehouse> getAllTheWarehouseMatchesForClient(String username) {
        AppClient currentUserAppClient = this.userService.findAppClientByUsername(username);
        return currentUserAppClient.getWarehouse_matches();
    }

    @Override
    public void createWarehouse(Warehouse offer) {
        this.warehouseRepository.save(offer);
    }

}

