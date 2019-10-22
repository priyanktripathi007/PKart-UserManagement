package com.pramati.kart.userManagement.controller;

import com.pramati.kart.userManagement.entity.Address;
import com.pramati.kart.userManagement.repository.AddressRepository;
import com.pramati.kart.userManagement.utility.BeanUtility;
import com.pramati.kart.userManagement.utility.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class AddressController {
    @Autowired
    private AddressRepository repository;

    @Autowired
    private BeanUtility beanUtility;

    @GetMapping("/address/{id}")
    public Address getAddressByID(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);

    }

    @GetMapping("/address")
    public List<Address> getAllAddresses() {
        return repository.findAll();
    }

    @PostMapping("/address")
    public Response createAddress(@RequestBody Address address) {
        repository.saveAndFlush(address);
        return new Response("Address created successfully");
    }

    @PutMapping("/address/{id}")
    public Address updateEmployee(@PathVariable("id") Long id,@RequestBody Address useraddress) {
        Address address=repository.findById(id).orElse(null);
        if(address==null) return null;
        BeanUtils.copyProperties(useraddress, address,beanUtility.getNullPropertyNames(useraddress));
        repository.saveAndFlush(address);
        return  repository.findById(id).orElse(null);
    }

    @DeleteMapping("/address/{id}")
    public Response deleteAddress(@PathVariable("id") Long id){
        repository.deleteById(id);
       return new Response("Address deleted successfully");

    }

    @PatchMapping("/address/{id}")
    public Address patchAddress(@PathVariable("id") Long id, @RequestBody HashMap<String, Object> fields) {
        Address address=repository.findById(id).orElse(null);
        if(address==null) return null;
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Address.class, k);
            if (field != null  )
            { System.out.println(field.toString());
                field.setAccessible(true);

                ReflectionUtils.setField(field, address,v);}
        });
        repository.saveAndFlush(address);
        return address;
    }
}
