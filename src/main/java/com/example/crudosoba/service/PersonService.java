package com.example.crudosoba.service;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person save(Person person){
        return personRepository.save(person);
    }

}
