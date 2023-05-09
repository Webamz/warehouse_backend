package com.warehousebackend.service;


import com.warehousebackend.model.entities.UserRoleEntity;
import com.warehousebackend.model.entities.enums.UserRoleEnum;

public interface UserRoleService {
    UserRoleEntity getUserRoleByEnumName(UserRoleEnum userRoleEnum);

    UserRoleEntity saveRole(UserRoleEntity userRoleEntity);
}
