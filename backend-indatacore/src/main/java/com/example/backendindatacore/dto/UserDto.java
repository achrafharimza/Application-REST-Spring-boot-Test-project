package com.example.backendindatacore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    public UserDto(String nom, String prenom, String email, String entreprise) {
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.entreprise=entreprise;
    }

    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String entreprise;


}
