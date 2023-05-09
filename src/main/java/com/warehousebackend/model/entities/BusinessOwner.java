package com.warehousebackend.model.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "business_owners")
public class BusinessOwner extends UserEntity {
    private String businessName;
    private String address;
    private Set<Warehouse> warehouse_offers;

    public BusinessOwner(String username, String email, List<UserRoleEntity> roles, String password, String businessName, String address) {
        super(username, email, roles, password);
        this.businessName = businessName;
        this.address = address;
    }

    public BusinessOwner() {
    }

    @Column(name = "business_name", nullable = false)
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Column(nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    public Set<Warehouse> getWarehouse_offers() {
        return warehouse_offers;
    }

    public void setWarehouse_offers(Set<Warehouse> warehouse_offers) {
        this.warehouse_offers = warehouse_offers;
    }
}
