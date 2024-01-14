package com.example.crudosoba.controller;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    @PostMapping("/save")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @PutMapping("/login")
    public ResponseEntity<Person> login(@RequestBody Person person) throws Exception {
        return ResponseEntity.ok(personService.login(person));
    }

    /// localhost:8081/delete/?id=0
    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePersonById(@RequestParam("id") Integer id) {
        personService.deletePersonById(id);
        return ResponseEntity.ok("ok");
    }

}
