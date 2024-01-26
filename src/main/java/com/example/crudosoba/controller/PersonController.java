package com.example.crudosoba.controller;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
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
    public ResponseEntity<List<Person>> findAll() {
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
    public ResponseEntity<String> loadDataFromCSV(@RequestParam("id") Integer id, @RequestParam("pathToCSV") String pathToCSV) {
        try {
            personService.loadDataFromCSV(id, pathToCSV);
            return ResponseEntity.ok("Data loaded");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/exportData")
    public ResponseEntity<String> exportDataToCSV(@RequestParam("id") Integer id, @RequestParam("pathToCSV") String pathToCSV) throws SQLException, IOException {
        personService.exportDataToCSV(id, pathToCSV);
        return ResponseEntity.ok("Data exported");
    }

    // Znajdź maksymalną długość hasła w bazie danych
    @GetMapping("/findMaxPasswordLength")
    public ResponseEntity<Integer> findMaxPasswordLength() {
        return ResponseEntity.ok(personService.findMaxPasswordLength());
    }

    // Policz liczbę osób w bazie danych dla każdej płci
    @GetMapping("/countByGender")
    public ResponseEntity<List<Object[]>> countByGender() {
        return ResponseEntity.ok(personService.countByGender());
    }

    // Oblicz średnią długość imion osób w bazie danych
    @GetMapping("/findAverageNameLength")
    public ResponseEntity<Double> findAverageNameLength() {
        return ResponseEntity.ok(personService.findAverageNameLength());
    }

    // Pobierz informacje o osobie razem z typem karty, ale tylko dla osób, które są administratorami (isAdmin = true)
    @GetMapping("/findAdminsWithCardType")
    public ResponseEntity<List<Object[]>> findAdminsWithCardType() {
        return ResponseEntity.ok(personService.findAdminsWithCardType());
    }

    // Znajdź liczbę osób w bazie danych dla każdego rodzaju karty (CardType)
    @GetMapping("/countByCardType")
    public ResponseEntity<List<Object[]>> countByCardType() {
        return ResponseEntity.ok(personService.countByCardType());
    }

    // Oblicz średnią długość haseł w bazie danych
    @GetMapping("/findAveragePasswordLength")
    public ResponseEntity<Double> findAveragePasswordLength() {
        return ResponseEntity.ok(personService.findAveragePasswordLength());
    }


}
