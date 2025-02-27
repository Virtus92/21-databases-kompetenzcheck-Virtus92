package org.example;

public interface PersonDAO {
    int createPerson(String Vorname, String Nachname) throws RuntimeException;
    int createPerson(String vorname, String nachname, Person.Geschlecht geschlecht, String geburtsdatum, Address address) throws RuntimeException;
    int createPerson(String vorname, String nachname, Person.Geschlecht geschlecht, String geburtsdatum) throws RuntimeException;

    Person getPerson(int id) throws RuntimeException;

    void updatePersonVorname(Person person, String vorname) throws RuntimeException;
    void updatePersonNachname(Person person, String nachname) throws RuntimeException;
    void updatePersonGeschlecht(Person person, Person.Geschlecht geschlecht) throws RuntimeException;
    void updatePersonAdresse(Person person, Address address) throws RuntimeException;
    void updatePersonGeburtsdatum(Person person, String geburtsdatum) throws RuntimeException;

    void deletePerson(int id) throws RuntimeException;
}
