package com.example.crudosoba.service;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.repository.PersonRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    @Getter
    private Integer loggedInUserId;

    public Person save(Person person) {
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
        if (person.getPassword().equals(password)) {
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
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()){
            return person.get();
        }
        throw new IllegalArgumentException();

    }

    public Person updatePersonById(Integer id, Person person) {
        Person personToUpdate = getPersonById(id);
            if (!person.getName().isEmpty()) {
                personToUpdate.setName(person.getName());
            }
            if (!person.getSurname().isEmpty()) {
                personToUpdate.setSurname(person.getSurname());
            }
            if (!person.getEmail().isEmpty()) {
                personToUpdate.setEmail(person.getEmail());
            }
            if (!person.getPassword().isEmpty()) {
                personToUpdate.setPassword(person.getPassword());
            }

            return personRepository.save(personToUpdate);

    }

    private boolean isUserLoggedIn(Integer userId) {
        return userId.equals(loggedInUserId);
    }
}
