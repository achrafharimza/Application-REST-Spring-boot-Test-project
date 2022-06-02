package com.example.backendindatacore.services;


import com.example.backendindatacore.dto.UserDto;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

public interface UserService  {
    List<UserDto> getUsers();
    UserDto addUser(UserDto user);


    List<UserDto> getUsersFromCsv();


}
