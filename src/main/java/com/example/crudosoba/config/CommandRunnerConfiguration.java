package com.example.crudosoba.config;

import com.example.crudosoba.model.Person;
import com.example.crudosoba.model.enums.CardType;
import com.example.crudosoba.model.enums.Gender;
import com.example.crudosoba.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Log
@Configuration
public class CommandRunnerConfiguration implements CommandLineRunner {
    private final PersonService personService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Feeding admin data...");
        personService.save(Person.builder().email("admin@admin.pl").password("Tajemnica1234$")
                .isAdmin(true).cardNumber("0000000000000000").gender(Gender.OTHER)
                .name("Pan").surname("Admin").cardType(CardType.OTHER).build());
    }
}
