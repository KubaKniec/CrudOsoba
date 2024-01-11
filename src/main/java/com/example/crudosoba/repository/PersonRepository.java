package com.example.crudosoba.repository;

import com.example.crudosoba.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
