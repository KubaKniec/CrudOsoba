package com.example.crudosoba.controller;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.service.PersonService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private Integer loggedInUserId;
    @PostMapping("/save")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @GetMapping("/login")
    public ResponseEntity<Person> login(@RequestParam String email, @RequestParam String password) {
     return ResponseEntity.ok(personService.login(email, password));
    }

    @PutMapping("/logout")
    public ResponseEntity<String> logout() {
        loggedInUserId = null;
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/loggedInUser")
    public ResponseEntity<Integer> getLoggedInUserId() {
        return ResponseEntity.ok(loggedInUserId);
    }

    @GetMapping("/getById")
    public ResponseEntity<Person> getPersonById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    // /getByEmail?
    @GetMapping("/getByEmail")
    public ResponseEntity<Person> getPersonByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(personService.getPersonByEmail(email));
    }

    // /localhost:8081/delete?id=0
    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deletePersonById(@RequestParam("id") Integer id) {
        personService.deletePersonById(id);
        return ResponseEntity.ok(id);
    }

    // /localhost:8081/update/0
    @PutMapping("/update/{id}")
    public ResponseEntity<Person> updatePersonById(@PathVariable("id") Integer id, @RequestBody Person person) {
        return ResponseEntity.ok(personService.updatePersonById(id, person));
    }




}
