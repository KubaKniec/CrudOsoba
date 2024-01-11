package com.example.crudosoba.controller;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    @PostMapping("/save")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

}
