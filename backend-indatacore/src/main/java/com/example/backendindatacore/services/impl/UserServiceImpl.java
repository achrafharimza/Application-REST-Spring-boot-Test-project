package com.example.backendindatacore.services.impl;


import com.example.backendindatacore.dto.UserDto;
import com.example.backendindatacore.dto.services.IMapClassWithDto;
import com.example.backendindatacore.entities.UserEntity;
import com.example.backendindatacore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    com.example.backendindatacore.repositories.userRepository userRepository;
    @Autowired
    IMapClassWithDto<UserEntity, UserDto> userMapping;

    @Value("${csv.path}")
    private String csvPath;

    @Override
    public List<UserDto> getUsers() {
        log.info("get all Users method");
        List<UserEntity> users = userRepository.findAll();
        return userMapping.convertListToListDto(users,UserDto.class);
    }

    @Override
    public UserDto addUser(UserDto user) {
        log.info("add user method");
        UserEntity userEntity = userMapping.convertToEntity(user, UserEntity.class);

        userEntity = userRepository.save(userEntity);

        return userMapping.convertToDto(userEntity, UserDto.class);
    }



    @EventListener(ApplicationReadyEvent.class)
    @Override
    public List<UserDto> getUsersFromCsv() {
        log.info("get Users From Csv");
        String line = "";
        String splitBy = ";";
        List<UserDto> listUserDto =new ArrayList<>();
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(csvPath));
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] userCsv = line.split(splitBy);
                //use comma as separator

                System.out.println("user-From-Csv=" + Arrays.toString(userCsv));

                UserDto csvUser=new UserDto(userCsv[0],userCsv[1],userCsv[2],userCsv[3]);
                listUserDto.add(csvUser);
            }
        }
        catch(IOException e) {
            log.error("File infound");
            e.printStackTrace();
        }

        return listUserDto;
    }


}