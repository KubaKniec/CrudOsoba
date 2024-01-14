package com.example.crudosoba.service;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Person getPersonByEmail(String email) {
        Optional<Person> person = personRepository.getPersonByEmail(email);
        return person.orElse(null);
    }

    public void deletePersonById(Integer id) {
        personRepository.deleteById(id);
    }


    public Person login(Person person) throws Exception {
        Optional<Person> personFromDB = personRepository.getPersonByEmail(person.getEmail());
        if (personFromDB.isPresent()) {
            if (personFromDB.get().getPassword().equals(person.getPassword())) {
                personFromDB.get().setLoggedIn(true);
                return personRepository.save(personFromDB.get());
            } else throw new Exception("Bad Password");
        }
        return null;
    }
}
