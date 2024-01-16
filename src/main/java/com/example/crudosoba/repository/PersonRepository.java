package com.example.crudosoba.repository;

import com.example.crudosoba.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person getPersonByEmail(String email);
}
