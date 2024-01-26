package com.example.crudosoba.repository;

import com.example.crudosoba.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> getPersonByEmail(String email);

    // Funkcje agregujące
    // Znajdź maksymalną długość hasła w bazie danych
    @Query("SELECT MAX(LENGTH(p.password)) FROM Person p")
    Integer findMaxPasswordLength();

    // Policz liczbę osób w bazie danych dla każdej płci
    @Query("SELECT p.gender, COUNT(p) FROM Person p GROUP BY p.gender")
    List<Object[]> countByGender();

    // Oblicz średnią długość imion osób w bazie danych
    @Query("SELECT AVG(LENGTH(p.name)) FROM Person p")
    Double findAverageNameLength();

    // Zapytania, które nie są typu CRUD
    // Pobierz informacje o osobie razem z typem karty, ale tylko dla osób, które są administratorami
    @Query("SELECT p, p.cardType FROM Person p WHERE p.isAdmin = true")
    List<Object[]> findAdminsWithCardType();

    // Znajdź liczbę osób w bazie danych dla każdego rodzaju karty (CardType)
    @Query("SELECT p.cardType, COUNT(p) FROM Person p GROUP BY p.cardType")
    List<Object[]> countByCardType();

    // Oblicz średnią długość haseł w bazie danych
    @Query("SELECT AVG(LENGTH(p.password)) FROM Person p")
    Double findAveragePasswordLength();


}
