package com.example.crudosoba.model;

import com.example.crudosoba.model.enums.CardType;
import com.example.crudosoba.model.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
//    private Gender gender;
//    private CardType cardType;
//    private Long cardNumber;
//    private boolean isLoggedIn = false;
}


