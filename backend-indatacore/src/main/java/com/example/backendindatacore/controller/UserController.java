package com.example.backendindatacore.controller;

import com.example.backendindatacore.dto.UserDto;
import com.example.backendindatacore.services.UserService;
import com.github.javafaker.Faker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;





@RestController
@RequestMapping("/user")
@Api(tags = "Profil d'un utilisateur ", value = "Profil utilisateur controller")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    Faker faker;

    //------- All users : -------------------------------------------------------------------

    @GetMapping("/all")
    @ApiOperation(value = "Afficher la liste des profils", notes ="Cette methode permet d'afficher une liste des profils ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Liste des profils trouvé dans BD"),
            @ApiResponse(code = 404, message = "Liste des profils  n'existe pas dans BD"),
            @ApiResponse(code = 500, message = "Une erreur système s'est produite"),
            @ApiResponse(code = 401, message = "Pas d'autorisation"),
            @ApiResponse(code = 403, message = "Acces interdit")
    })
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> userDto = userService.getUsers();
        return ResponseEntity.ok(userDto);
    }

    //------- Add user : ------------------------------------------------------------------
    @PostMapping("/add")
    @ApiOperation(value = "ajouter un utilisateur", notes ="Cette methode permet d'ajouter un utilisateur")
    public ResponseEntity<UserDto> add(@RequestBody UserDto userDto )  {
        UserDto newuser = userService.addUser(userDto);
        return new ResponseEntity<UserDto>(newuser, HttpStatus.CREATED);
    }
    //------- Add random user : ------------------------------------------------------------------
    @PostMapping("/random")
    @ApiOperation(value = "ajouter un utilisateur aléatoire", notes ="Cette methode permet d'ajouter un utilisateur en générant des valeurs aléatoires")
    public ResponseEntity<UserDto> addRandom()  {
        String firstName = faker.address().firstName();
        String lastName = faker.address().lastName();
        String email = faker.internet().emailAddress();
        String entreprise = faker.company().name();
        UserDto randomuser=new UserDto(firstName,lastName,email,entreprise);
        UserDto newuser = userService.addUser(randomuser);
        return new ResponseEntity<UserDto>(newuser, HttpStatus.CREATED);
    }
    //------- get users from CSV  : --------------------------------------------------------------

    @GetMapping("/csv")
    @ApiOperation(value = "Afficher la liste des profils à partir d'un ficjier CSV ", notes ="Cette methode permet d'afficher une liste des profils de CSV")
    public ResponseEntity<List<UserDto>> getAllcsv(){
        List<UserDto> userDto = userService.getUsersFromCsv();
        return ResponseEntity.ok(userDto);
    }}
