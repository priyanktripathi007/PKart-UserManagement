package com.pramati.kart.userManagement.controller;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.pramati.kart.userManagement.entity.User;
import com.pramati.kart.userManagement.repository.UserRepository;
import com.pramati.kart.userManagement.utility.BeanUtility;
import com.pramati.kart.userManagement.utility.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BeanUtility beanUtility;

    @GetMapping("/user/{id}")
    public User getEmployeeByID(@PathVariable("id") long id) {
        return repository.findById(id).orElse(null);

    }

    @GetMapping("/user")
    public List<User> getAllEmployees() {
        return repository.findAll();
    }

    @PostMapping("/user")
    public Response createUSer(@RequestBody User user) {
        repository.saveAndFlush(user);
        return new Response("User created successfully");
    }

    @PutMapping("/user/{id}")
    public User updateEmployee(@PathVariable("id") Long id,@RequestBody User userdata) {
        User user=repository.findById(id).orElse(null);
        if(user==null) return null;
        BeanUtils.copyProperties(userdata, user,beanUtility.getNullPropertyNames(userdata));
        repository.saveAndFlush(user);
        return  repository.findById(id).orElse(null);
    }

    @DeleteMapping("/user/{id}")
    public Response deleteUser(@PathVariable("id") Long id){
        repository.deleteById(id);
        return new Response("User deleted successfully");

    }

    @PatchMapping("/user/{id}")
    public User patchUser(@PathVariable("id") Long id, @RequestBody HashMap<String, Object> fields) {
        User user=repository.findById(id).orElse(null);
        String[] attributes = new String[]{"createdTime", "lastModifiedTime", "id","createdBy"};
        List<String> list = Arrays.asList(attributes);
        if(user==null) return null;
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(User.class, k);
            if (field != null  && !list.contains(field.getName()) )
            { System.out.println(field.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, user,v);}
        });
        repository.saveAndFlush(user);
        return user;
    }
}






