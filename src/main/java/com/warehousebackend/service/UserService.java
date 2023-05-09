package com.warehousebackend.service;



import com.warehousebackend.model.dto.AppClientSignUpDto;
import com.warehousebackend.model.dto.BusinessRegisterDto;
import com.warehousebackend.model.entities.AppClient;
import com.warehousebackend.model.entities.BusinessOwner;
import com.warehousebackend.model.entities.UserEntity;
import com.warehousebackend.model.entities.Warehouse;

import java.util.List;

public interface UserService {
    List<UserEntity> seedUsersAndUserRoles();

    AppClient register(AppClientSignUpDto user);

    BusinessOwner registerBusiness(BusinessRegisterDto business);

    BusinessOwner saveUpdatedUser(BusinessOwner businessOwner);

    AppClient saveUpdatedUserClient(AppClient appClient);

    UserEntity findUserById(Long userId);

    UserEntity findUserByEmail(String email);

    boolean deleteUser(Long id);

    BusinessOwner findBusinessOwnerById(Long id);

    UserEntity findUserByUsername(String username);

    boolean userExists(String username, String email);

    void saveUserWithUpdatedPassword(UserEntity userEntity);

    AppClient findAppClientById(Long clientId);

    void findAndRemoveWarehouseFromClientsRecords(Warehouse warehouse);
    
    boolean businessExists(String businessName);

    AppClient findAppClientByUsername(String username);

    BusinessOwner findBusinessByUsername(String username);
}


