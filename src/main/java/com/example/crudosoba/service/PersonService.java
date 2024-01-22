package com.example.crudosoba.service;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.model.enums.CardType;
import com.example.crudosoba.model.enums.Gender;
import com.example.crudosoba.repository.PersonRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

@Service
@RequiredArgsConstructor
@Validated
public class PersonService {
    private final PersonRepository personRepository;

    public Person save(@Valid Person person) { //TODO dokończyć później nie przechodzi regex z password
        if (personRepository.getPersonByEmail(person.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Person already exist");
        }
        return personRepository.save(person);

        //TODO WALIDACJA HASŁA I EMAIL
//        final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-]).{6,}$";
//        final String emailRegex = "/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/";
//        //string cardNumberRegex
//        if (person.getPassword().matches(passwordRegex) && person.getEmail().matches(emailRegex)) {
//            return personRepository.save(person);
//        } else {
//            throw new IllegalArgumentException("Invalid password or email");
//        }
    }
//    public Person save(Person person) {
//        return personRepository.save(person);
//    }

    public Boolean checkIsAdmin(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            if (person.get().getIsAdmin()) {
                return true;
            }
            throw new IllegalArgumentException("Person is not an ADMIN");
        }
        throw new IllegalArgumentException("No such person with provided id");
    }

    public Person getPersonByEmail(String email) {
        Optional<Person> person = personRepository.getPersonByEmail(email);
        if (person.isPresent()) {
            return person.get();
        } else
            throw new IllegalArgumentException("No such person with provided email");

    }

    public Integer deletePersonById(Integer id) {
        personRepository.deleteById(id);
        return id;
    }

    public Person login(String email, String password) {
        Person person = getPersonByEmail(email);
        if (person.getPassword().equals(password)) {
            return person;
        }
        throw new IllegalArgumentException("Invalid email or password");
    }

    public Person getPersonById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        }
        throw new IllegalArgumentException("No such person with provided id");

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
        if (!person.getGender().equals(personToUpdate.getGender())) {
            personToUpdate.setGender(person.getGender());
        }
        if (!person.getCardType().equals(personToUpdate.getCardType())) {
            personToUpdate.setCardType(person.getCardType());
        }
        if (!person.getCardNumber().isEmpty()) {
            personToUpdate.setCardNumber(person.getCardNumber());
        }
        return personRepository.save(personToUpdate);

    }

    public Person grandAdminById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setIsAdmin(true);
            return personRepository.save(person.get());
        } else {
            throw new IllegalArgumentException("No user with provided id");
        }

    }

    public Person revokeAdminById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setIsAdmin(false);
            return personRepository.save(person.get());
        } else {
            throw new IllegalArgumentException("No user with provided id");
        }
    }

    @Transactional //czasami działa za 2 próbą jeżeli wysyłam request z fronta. Nie wiem czemu
    public void loadDataFromCSV(String pathToCsv) throws IOException {
        String line;
        String csvSplitBy = ",";
        pathToCsv = "C:/" + pathToCsv;
        List<Person> persons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToCsv))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String name = data[0];
                String surname = data[1];
                String email = data[2];
                String password = data[3];
                Gender gender = Gender.valueOf(data[4]);
                CardType cardType = CardType.valueOf(data[5]);
                String cardNumber = data[6];
                Boolean isAdmin = Boolean.parseBoolean(data[7]);
                Person person = new Person();

                person.setName(name);
                person.setSurname(surname);
                person.setEmail(email);
                person.setPassword(password);
                person.setGender(gender);
                person.setCardType(cardType);
                person.setCardNumber(cardNumber);
                person.setIsAdmin(isAdmin);
                persons.add(person);
            }
            personRepository.saveAll(persons);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
