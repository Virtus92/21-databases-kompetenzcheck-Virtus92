package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n---Address Tests---");

        // Create Adresses
        Address address1 = new Address("Peter-Behrens-Platz", 4, 4020, "Linz");
        Address address2 = new Address("Hauptstraße", 10, 1010, "Wien");
        System.out.println("Adressen erstellt!");

        int addressId1 = address1.getId();
        int addressId2 = address2.getId();

        System.out.println("\n---Get Address by ID Test---");
        Address retrievedAddress1 = Address.getAddress(addressId1);
        Address retrievedAddress2 = Address.getAddress(addressId2);
        System.out.println(retrievedAddress1);
        System.out.println(retrievedAddress2);

        System.out.println("\nReady for the Address Change Test?");
        sc.nextLine();

        // Adresse ändern
        System.out.println("\n---Change Address Test---");
        retrievedAddress1.updateAddress("Neue Straße", 15, 5000, "Salzburg");
        System.out.println("Adresse geändert!");
        System.out.println(Address.getAddress(addressId1));

        System.out.println("\nReady for the Address Integrity Test?");
        sc.nextLine();

        // Adresse löschen
        System.out.println("\n---Integrity Test (Delete Address)---");
        Address.deleteAddress(address1);
        Address.deleteAddress(address2);

        // Prüfen, ob die Adresse gelöscht wurde
        Address deletedAddress1 = Address.getAddress(addressId1);
        Address deletedAddress2 = Address.getAddress(addressId2);

        System.out.println("Adresse 1 nach Löschung: " + deletedAddress1);
        System.out.println("Adresse 2 nach Löschung: " + deletedAddress2);

        //Create Test
        System.out.println("---Create Test---");
        Person person = new Person("Dinel", "Kurtovic");
        Person person0 = new Person("Dinela", "Kurtovic", Person.Geschlecht.WEIBLICH, "11.09.1975");
        Address address = new Address("Wimmerstraße", 5, 4060, "Leonding");
        Person person1 = new Person("Dinel", "Kurtovic", Person.Geschlecht.MÄNNLICH, "10.06.2001", address);
        System.out.println("Create Success!");

        //Get the ID
        int id = person.getId();
        int id1 = person0.getId();
        int id2 = person1.getId();

        //Get Persons by ID
        Person personX = Person.getPerson(id);
        Person personY = Person.getPerson(id1);
        Person personZ = Person.getPerson(id2);

        //Print Persons
        System.out.println("\n---Get by ID Test---");
        System.out.println(personX);
        System.out.println(personY);
        System.out.println(personZ);


        System.out.println("Ready for the Change Test?");
        sc.nextLine();

        //Change Values
        System.out.println("\n---Change Values Test---");
        personX.setFirstName("Alex");
        personY.setLastName("Osmanovic");
        personY.setFirstName("Dinel");
        personY.setAdresse(address);
        personY.setGender(Person.Geschlecht.MÄNNLICH);
        Address addressZ = new Address("Peter-Behrens-Platz", 4, 4020, "Linz");
        personZ.setAdresse(null);
        personX.setAdresse(addressZ);

        //Consistency
        System.out.println(person);
        System.out.println(person0);
        System.out.println(person1);

        System.out.println("Ready for the Integrity Test?");
        sc.nextLine();

        //Integrity
        System.out.println("\n---Integrity Test---");
        Person.deletePerson(person);
        Person.deletePerson(person0);
        Person.deletePerson(person1);
        Address.deleteAddress(addressZ);
        Address.deleteAddress(address);
        System.out.println(personX);
        System.out.println(personY);
        System.out.println(personZ);
    }
}