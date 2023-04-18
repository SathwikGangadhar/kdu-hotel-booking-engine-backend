package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.entity.User;
import com.kdu.IBE.model.responseDto.UserModel;
import com.kdu.IBE.repository.UserRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(EndPointConstants.USER)
public class UserController {
    @Autowired
    UserRepository userRepository;
    @PostMapping(EndPointConstants.ADD_USER)
    public ResponseEntity<String> addUser(@Valid @RequestBody UserModel userModel , BindingResult result){
        if(result.hasErrors()){
            throw new ObjectNotFoundException("Value passed was invalid","");
        }
        User user=new User(userModel.getUserId());
        userRepository.save(user);
        return new ResponseEntity<>("added successfully", HttpStatus.OK);
    }
}
