package com.romc.customer.model.service;

import com.romc.customer.model.dao.CustomerRepository;
import com.romc.customer.model.entity.CustomerEnt;
import com.romc.customer.model.entity.RegionEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<CustomerEnt> findCustomerEntAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerEnt> findCustomerEntsByRegionEnt(RegionEnt regionEnt) {
        return customerRepository.findByRegionEnt(regionEnt);
    }

    @Override
    public CustomerEnt createCustomerEnt(CustomerEnt customerEnt) {
        CustomerEnt customerdb = customerRepository.findByNumberID(customerEnt.getNumberID());
        if(customerdb!= null){
            return customerdb;
        }
        customerEnt.setState("CREATED");
        customerdb = customerRepository.save(customerEnt);
        return customerdb;
    }

    @Override
    public CustomerEnt updateCustomerEnt(CustomerEnt customerEnt) {
        CustomerEnt customerdb = getCustomerEnt(customerEnt.getId());
        if(customerdb == null){
            return null;
        }
        customerdb.setFirstname(customerEnt.getFirstname());
        customerdb.setLastname(customerEnt.getLastname());
        customerdb.setEmail(customerEnt.getEmail());
        customerdb.setPhotourl(customerEnt.getPhotourl());

        return customerRepository.save(customerdb);
    }

    @Override
    public CustomerEnt deleteCustomerEnt(CustomerEnt customerEnt) {
        CustomerEnt customerdb = getCustomerEnt(customerEnt.getId());
        if(customerdb == null){
            return null;
        }
        customerEnt.setState("DELETED");
        return customerRepository.save(customerEnt);
    }

    @Override
    public CustomerEnt getCustomerEnt(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
