package com.romc.customer.model.dao;

import com.romc.customer.model.entity.CustomerEnt;
import com.romc.customer.model.entity.RegionEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEnt, Long> {
    public CustomerEnt findByNumberID(String numberID);
    public List<CustomerEnt> findByRegionEnt(RegionEnt regionEnt);
}
