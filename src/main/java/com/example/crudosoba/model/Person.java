package com.example.crudosoba.model;

import com.example.crudosoba.model.enums.CardType;
import com.example.crudosoba.model.enums.Gender;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
//    @CsvBindByName(column = "id")
    private Integer id;

//    @CsvBindByName(column = "name")
    private String name;

//    @CsvBindByName(column = "surname")
    private String surname;

//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
//    @CsvBindByName(column = "email")
    private String email;

//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-]).{6,}$")
//    @CsvBindByName(column = "password")
    private String password;

    @Enumerated(EnumType.STRING)
//    @CsvBindByName(column = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
//    @CsvBindByName(column = "cardType")
    private CardType cardType;

//    @Pattern(regexp = "^\\d{16}$")
//    @CsvBindByName(column = "cardNumber")
    private String cardNumber; //String, aby nr karty mógł zaczynać się od "0"

//    @CsvBindByName(column = "isAdmin")
    private Boolean isAdmin;

    @Override
    public String toString() {
        return id + "," +
                name + "," +
                surname + "," +
                email + "," +
                password + "," +
                gender + "," +
                cardType + "," +
                cardNumber + "," +
                isAdmin;
    }
}


