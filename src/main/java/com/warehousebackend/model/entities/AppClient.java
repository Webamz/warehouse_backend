package com.warehousebackend.model.entities;


import com.warehousebackend.model.entities.enums.GenderEnum;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_clients")
public class AppClient extends UserEntity implements Serializable {
    private String fullName;
    private GenderEnum gender;
    private Test testResults;
    private Set<Warehouse> warehouse_matches;
    private List<Warehouse> saved_theWarehouses;

    public AppClient() {
    }

    public AppClient(String username, String email, List<UserRoleEntity> roles, String password, String fullName, GenderEnum gender) {
        super(username, email, roles, password);
        this.fullName = fullName;
        this.gender = gender;
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Warehouse> getWarehouse_matches() {
        return warehouse_matches;
    }

    public void setWarehouse_matches(Set<Warehouse> warehouse_matches) {
        this.warehouse_matches = warehouse_matches;
    }

    @OneToOne(cascade = CascadeType.REMOVE)
    public Test getTestResults() {
        return testResults;
    }

    public void setTestResults(Test testResults) {
        this.testResults = testResults;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Warehouse> getSaved_theWarehouses() {
        return saved_theWarehouses;
    }

    public void setSaved_theWarehouses(List<Warehouse> saved_theWarehouses) {
        this.saved_theWarehouses = saved_theWarehouses;
    }
}
