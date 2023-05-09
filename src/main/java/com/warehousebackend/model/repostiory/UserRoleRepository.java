package com.warehousebackend.model.repostiory;


import com.warehousebackend.model.entities.UserRoleEntity;
import com.warehousebackend.model.entities.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByRole(UserRoleEnum roleEnum);
}
