package com.romc.customer.model.service;

import com.romc.customer.model.entity.CustomerEnt;
import com.romc.customer.model.entity.RegionEnt;

import java.util.List;

public interface CustomerService {
    public List<CustomerEnt> findCustomerEntAll();
    public List<CustomerEnt> findCustomerEntsByRegionEnt(RegionEnt regionEnt);

    public CustomerEnt createCustomerEnt(CustomerEnt customerEnt);
    public CustomerEnt updateCustomerEnt(CustomerEnt customerEnt);
    public CustomerEnt deleteCustomerEnt(CustomerEnt customerEnt);
    public CustomerEnt getCustomerEnt(Long id);

}
