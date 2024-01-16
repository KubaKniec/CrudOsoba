package com.example.crudosoba.service;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.repository.PersonRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    @Getter
    private Integer loggedInUserId;

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Person getPersonByEmail(String email) {
//        Optional<Person> person = personRepository.getPersonByEmail(email);
        return personRepository.getPersonByEmail(email);
//        return person.orElse(null);
    }

    public Integer deletePersonById(Integer id) {
            personRepository.deleteById(id);
            return id;
    }

    public Person login(String email, String password) {
        Person person = getPersonByEmail(email);
        if (person.getPassword().equals(password)){
            loggedInUserId = person.getId();
            return person;
        }
        return null;
    }

    public String logout() {
        loggedInUserId = null;
        return "Logged out successfully";
    }

    public Person getPersonById(Integer id) {
        if (isUserLoggedIn(id)){
            return personRepository.findById(id).orElse(null);
        } else {
            return null; //TODO czy tutaj dodac cos w stylu HttpStatus.UNAUTHORIZED
        }
    }

    private boolean isUserLoggedIn(Integer userId) {
        return userId.equals(loggedInUserId);
    }
}
