package com.example.crudosoba.service;

import com.example.crudosoba.ExceptionHandler.Exceptions.*;
import com.example.crudosoba.model.Person;
import com.example.crudosoba.model.enums.CardType;
import com.example.crudosoba.model.enums.Gender;
import com.example.crudosoba.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Validated
public class PersonService {
    private final PersonRepository personRepository;
    private final JdbcTemplate jdbcTemplate;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    private Boolean isInputValid(String input, String regex) {
        if (input == null)
            return false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();

    }

    public Person save(Person person) { //TODO dokończyć później nie przechodzi regex z password
        String passwordRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,20}$";
        if (personRepository.getPersonByEmail(person.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException("Person already exist");
        }
        if (
                isInputValid(person.getEmail(), "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").equals(true) &&
                        isInputValid(person.getCardNumber(), "^\\d{16}$").equals(true) &&
                        isInputValid(person.getPassword(), passwordRegex).equals(true)) {
            return personRepository.save(person);
        } else {
            throw new InvalidCredentialsException("Email, Password or Card Number is invalid");
        }
    }

    public Boolean checkIsAdmin(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            if (person.get().getIsAdmin()) {
                return true;
            }
            throw new NotAnAdminException("Person is not an ADMIN");
        }
        throw new PersonNotFoundException("No such person with provided id");
    }

    public Person getPersonByEmail(String email) {
        Optional<Person> person = personRepository.getPersonByEmail(email);
        if (person.isPresent()) {
            return person.get();
        } else
            throw new PersonNotFoundException("No such person with provided email");

    }

    public Integer deletePersonById(Integer id) {
        try {
            getPersonById(id);
            personRepository.deleteById(id);
            return id;
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException("No such person with provided id");
        }
    }

    public Person login(String email, String password) {
        Person person = getPersonByEmail(email);
        if (person.getPassword().equals(password)) {
            return person;
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }

    public Person getPersonById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        }
        throw new PersonNotFoundException("No such person with provided id");

    }

    public Person updatePersonById(Integer id, Person person) { //TODO handle all exceptions in enums
        String passwordRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{6,20}$";
        Person personToUpdate;
        try {
            personToUpdate = getPersonById(id);
        } catch (PersonNotFoundException e) {
            throw new PersonNotFoundException("No such person with provided id");
        }


        if (person.getName() != null && !person.getName().isEmpty()) {
            personToUpdate.setName(person.getName());
        }
        if (person.getSurname() != null && !person.getSurname().isEmpty()) {
            personToUpdate.setSurname(person.getSurname());
        }
        if (person.getEmail() != null && !person.getEmail().isEmpty()) {
            if (isInputValid(person.getEmail(), "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").equals(true)) {
                personToUpdate.setEmail(person.getEmail());
            } else {
                throw new InvalidEmailException("Invalid Email");
            }
        }
        if (person.getPassword() != null && !person.getPassword().isEmpty()) {
            if (isInputValid(person.getPassword(), passwordRegex).equals(true)) {
                personToUpdate.setPassword(person.getPassword());
            } else {
                throw new InvalidPasswordException("Invalid Password");
            }
        }
        if (person.getGender() != null && !person.getGender().equals(personToUpdate.getGender())) {
            try {
                checkGenderEnumValue(String.valueOf(person.getGender()));
                personToUpdate.setGender(person.getGender());
            } catch (InvalidEnumValueException e) {
                throw new InvalidEnumValueException(e.getMessage());
            }
//                    if (checkGenderEnumValue(String.valueOf(person.getGender())))
//                        personToUpdate.setGender(person.getGender());
        }

        if (person.getCardType() != null && !person.getCardType().equals(personToUpdate.getCardType())) {
//                    if (checkCardTypeEnumValue(String.valueOf(person.getCardType())))
//                        personToUpdate.setCardType(person.getCardType());
            try {
                checkCardTypeEnumValue(String.valueOf(person.getCardType()));
                personToUpdate.setCardType(person.getCardType());
            } catch (InvalidEnumValueException e) {
                throw new InvalidEnumValueException(e.getMessage());
            }
        }

        if (person.getCardNumber() != null && !person.getCardNumber().isEmpty()) {
            if (isInputValid(person.getCardNumber(), "^\\d{16}$").equals(true)) {
                personToUpdate.setCardNumber(person.getCardNumber());//*
            } else {
                throw new InvalidCardNumberException("Invalid card Number");
            }
        }
        return personRepository.save(personToUpdate);
    }

    public Person grandAdminById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setIsAdmin(true);
            return personRepository.save(person.get());
        } else {
            throw new PersonNotFoundException("No user with provided id");
        }

    }

    public Person revokeAdminById(Integer id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setIsAdmin(false);
            return personRepository.save(person.get());
        } else {
            throw new PersonNotFoundException("No user with provided id");
        }
    }

    @Async
    public void loadDataFromCSV(Integer id, String pathToCsv) throws IOException, CSVFileNotFoundException {
        if (getPersonById(id).getIsAdmin() == null || !getPersonById(id).getIsAdmin())
            throw new NotAnAdminException("Person is not an admin");

        String line;
        String csvSplitBy = ",";

        try {
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
            } catch (FileNotFoundException e) {
                throw new CSVFileNotFoundException("Invalid path");
            }
        } catch (IllegalArgumentException e) {
            throw new ImportErrorException("Invalid argument in file");
        }

    }

    // path: C:\crud\data.csv
    @Async
    public void exportDataToCSV(Integer id, String pathToCSV) throws SQLException, IOException {
        if (getPersonById(id).getIsAdmin() == null || !getPersonById(id).getIsAdmin())
            throw new NotAnAdminException("Person is not an admin");

        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM person");
            if (!rows.isEmpty()) {
                try (FileWriter writer = new FileWriter(pathToCSV)) {
                    // Write CSV header
                    writer.append(String.join(",", rows.get(0).keySet())).append("\n");

                    // Write data
                    for (Map<String, Object> row : rows) {
//                    System.out.println(Thread.currentThread().getId() + ": " + row.values()); sprawdzenie czy wykonują się 2 lub wiecej na raz
//                    Thread.sleep(1000);
                        writer.append(String.join(",", row.values().stream().map(Object::toString).toArray(String[]::new)))
                                .append("\n");
                    }
                } catch (FileNotFoundException e) {
                    throw new CSVFileNotFoundException("Invalid path");
                }
//            catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            }
        } catch (IllegalArgumentException e) {
            throw new ExportErrorException("Something went wrong while exporting");
        }
    }

    public Integer findMaxPasswordLength() {
        return personRepository.findMaxPasswordLength();
    }

    public List<Object[]> countByGender() {
        return personRepository.countByGender();
    }

    public Double findAverageNameLength() {
        return personRepository.findAverageNameLength();
    }

    public List<Object[]> findAdminsWithCardType() {
        return personRepository.findAdminsWithCardType();
    }

    public List<Object[]> countByCardType() {
        return personRepository.countByCardType();
    }

    public Double findAveragePasswordLength() {
        return personRepository.findAveragePasswordLength();
    }


    private void checkGenderEnumValue(String input) {
        List<String> genderValues = List.of(
                String.valueOf(Gender.MALE),
                String.valueOf(Gender.FEMALE),
                String.valueOf(Gender.OTHER)
        );
        if (!genderValues.contains(input.toUpperCase())) {
            throw new InvalidEnumValueException("Input is not a Gender enum value");
        }
    }

    private void checkCardTypeEnumValue(String input) {
        List<String> cardTypeValues = List.of(
                String.valueOf(CardType.VISA),
                String.valueOf(CardType.MASTERCARD),
                String.valueOf(CardType.OTHER)
        );
        if (!cardTypeValues.contains(input.toUpperCase())) {
            throw new InvalidEnumValueException("Input is not a CardType enum value");
        }
    }
//    private Boolean checkGenderEnumValue (String input) {
//        List<String> genderValues = List.of(
//                String.valueOf(Gender.MALE),
//                String.valueOf(Gender.FEMALE),
//                String.valueOf(Gender.OTHER)
//                );
//
//        if (genderValues.contains(input.toUpperCase())) {
//            return true;
//        } else {
//            throw new InvalidEnumValueException("Input is not a Gender enum value");
//        }
//    }
//
//    private Boolean checkCardTypeEnumValue (String input) {
//        List<String> cardTypeValues = List.of(
//                String.valueOf(CardType.VISA),
//                String.valueOf(CardType.MASTERCARD),
//                String.valueOf(CardType.OTHER)
//        );
//
//        if (cardTypeValues.contains(input.toUpperCase())) {
//            return true;
//        } else {
//            throw new InvalidEnumValueException("Input is not an CardType enum value");
//        }
//    }
}




