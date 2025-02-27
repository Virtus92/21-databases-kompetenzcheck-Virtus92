package org.example;

import java.util.HashMap;

public class PersonenVerwaltung {
    private static final HashMap<Integer, Person> PersonenMap = new HashMap<>();
    private static final HashMap<Integer, Address> AdressenMap = new HashMap<>();
    private static final PersonDAO personDAO = new PersonenDAOImpl();
    private static final AddressDAO addressDAO = new AddressDAOImpl();

    public static int createPerson(Person person) {
        int id;
        if (person.getGender() == null) {
            id = personDAO.createPerson(person.getvorname(), person.getnachname());
        } else if (person.getAdresse() == null) {
            id = personDAO.createPerson(person.getvorname(), person.getnachname(), person.getGender(), person.getgeburtsdatum());
        } else {
            id = personDAO.createPerson(person.getvorname(), person.getnachname(), person.getGender(), person.getgeburtsdatum(), person.getAdresse());
        }
        PersonenMap.put(id, person);
        return id;
    }

    public static void createPerson(int id, Person person) {
        PersonenMap.put(id, person);
    }

    public static Person getPerson(int id) {
        return PersonenMap.get(id);
    }

    public static void deletePerson(Person person) {
        personDAO.deletePerson(person.getId());
        PersonenMap.remove(person.getId(), person);
    }

    public static int createAddress(Address address) {
        int id = addressDAO.createAddress(address);
        AdressenMap.put(id, address);
        return id;
    }

    public static void createAddress(int id, Address address) {
        AdressenMap.put(id, address);
    }

    public static Address getAddress(int id) {
        return AdressenMap.get(id);
    }

    public static void deleteAddress(Address address) {
        addressDAO.deleteAddress(address.getId());
        AdressenMap.remove(address.getId(), address);
    }

    @Override
    public String toString() {
        return "PersonenVerwaltung " + PersonenMap;
    }
}
