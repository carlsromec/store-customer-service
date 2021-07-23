package com.romc.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romc.customer.model.entity.CustomerEnt;
import com.romc.customer.model.entity.RegionEnt;
import com.romc.customer.model.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerEnt>> listAllCustomer(@RequestParam(name = "regionID", required = false) Long regionId){
        List<CustomerEnt> customerEnts = new ArrayList<>();
        if (null == regionId){
            customerEnts = customerService.findCustomerEntAll();
            if (customerEnts.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            RegionEnt regionEnt = new RegionEnt();
            regionEnt.setId(regionId);
            customerEnts = customerService.findCustomerEntsByRegionEnt(regionEnt);
            if (null == customerEnts){
                log.error("customer with region id {} not found.", regionId);
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(customerEnts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEnt> getCustomer(@PathVariable("id") Long id){
        log.info("Fetching customer with id {}", id);
        CustomerEnt customerEnt = customerService.getCustomerEnt(id);
        if(null == customerEnt){
            log.error("customer with id {} not found.",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerEnt);
    }

    @PostMapping
    public ResponseEntity<CustomerEnt> createCustomer(@Valid @RequestBody CustomerEnt customerEnt, BindingResult result){
        log.info("Creating customer : {}", customerEnt);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        CustomerEnt customerEntDB= customerService.createCustomerEnt(customerEnt);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerEntDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEnt> updateCustomer(@PathVariable("id") Long id,@RequestBody CustomerEnt customerEnt){
        log.info("updating customer with id{}: ", id);
        CustomerEnt currentCustomer = customerService.getCustomerEnt(id);
        if (null == currentCustomer){
            log.error("unable to update. Customer with id {} not found.", id);
                return ResponseEntity.notFound().build();
        }
        customerEnt.setId(id);
        currentCustomer = customerService.updateCustomerEnt(customerEnt);
        return ResponseEntity.ok(currentCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerEnt> deleteCustomer(@PathVariable("id") Long id){
        log.info("Fetching & Deleting Customer with id {} " + id);
        CustomerEnt customerEnt = customerService.getCustomerEnt(id);
        if (null == customerEnt){
            log.error("Unable to delete. Customer with id {} not found " + id);
            return ResponseEntity.notFound().build();
        }
        customerEnt = customerService.deleteCustomerEnt(customerEnt);
        return ResponseEntity.ok(customerEnt);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }



}
