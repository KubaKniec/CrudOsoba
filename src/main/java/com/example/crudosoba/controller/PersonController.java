package com.example.crudosoba.controller;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.service.PersonService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
    private Integer loggedInUserId;

    @PostMapping("/save")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.save(person));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Person>> findAll () {
        return ResponseEntity.ok(personService.findAll());
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
    //localhost:8081/isAdmin?id=0
    @GetMapping("/isAdmin")
    public ResponseEntity<Boolean> checkIsAdmin(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(personService.checkIsAdmin(id));

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

    @PutMapping("/grantAdmin/{id}")
    public ResponseEntity<Person> grandAdminById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(personService.grandAdminById(id));
    }

    @PutMapping("/revokeAdmin/{id}")
    public ResponseEntity<Person> revokeAdminById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(personService.revokeAdminById(id));
    }

    @PostMapping("/loadData")
    public ResponseEntity<String> loadDataFromCSV(@RequestParam("pathToCSV") String pathToCSV) {
        try {
            personService.loadDataFromCSV(pathToCSV);
            return ResponseEntity.ok("Data loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/exportData")
    public ResponseEntity<String> exportDataToCSV(@RequestParam("pathToCSV") String pathToCSV) {
        personService.exportDataToCSV(pathToCSV);
        return ResponseEntity.ok("Data exported");
    }



}
