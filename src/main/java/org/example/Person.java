package org.example;

public class Person {
    private int id;
    private static final PersonDAO personDAO = new PersonenDAOImpl();

    public enum Geschlecht {
        MÄNNLICH,
        WEIBLICH
    }

    private String firstName;
    private String lastName;
    private Geschlecht gender;
    private String birthdate;
    private Address address;

    //Konstruktoren
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = PersonenVerwaltung.createPerson(this);
    }

    public Person(String firstName, String lastName, Geschlecht gender, String birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.id = PersonenVerwaltung.createPerson(this);
    }

    public Person(String firstName, String lastName, Geschlecht gender, String birthdate, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.id = PersonenVerwaltung.createPerson(this);
    }

    //Konstruktoren für Personenverwaltung
    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName, Geschlecht gender, String birthdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public Person(int id, String firstName, String lastName, Geschlecht gender, String birthdate, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
    }

    //Abrufen

    public static Person getPerson(int id) throws Exception {
        Person mapPerson = PersonenVerwaltung.getPerson(id);
        Person dbPerson = personDAO.getPerson(id);
        if (mapPerson != null && dbPerson != null) {
            return mapPerson;
        } else if (dbPerson != null) {
            PersonenVerwaltung.createPerson(dbPerson.getId(), dbPerson);
            return dbPerson;
        } else {
            System.out.println("Fehler, Benutzer wurde nicht gefunden. Null wird verwendet.");
            return null;

            //Print oder Exception?

//            throw new Exception("Fehler, Benutzer wurde nicht gefunden.");
        }
    }

    public int getId() {
        return id;
    }
    public String getvorname() {
        return firstName;
    }
    public String getnachname() {
        return lastName;
    }
    public Geschlecht getGender() {
        return gender;
    }
    public Address getAdresse() {
        return address;
    }
    public String getgeburtsdatum() {
        return birthdate;
    }

    //Verändern
    public void setFirstName(String firstName) {
        personDAO.updatePersonVorname(this, firstName);
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        personDAO.updatePersonNachname(this, lastName);
        this.lastName = lastName;
    }
    public void setGender(Geschlecht gender) {
        personDAO.updatePersonGeschlecht(this, gender);
        this.gender = gender;
    }
    public void setAdresse(Address address) {
        personDAO.updatePersonAdresse(this, address);
        this.address = address;
    }
    public void setBirthdate(String birthdate) {
        personDAO.updatePersonGeburtsdatum(this, birthdate);
        this.birthdate = birthdate;
    }

    //Löschen
    public static void deletePerson(Person person) {
        PersonenVerwaltung.deletePerson(person);
        person.id = -1;
        person.firstName = null;
        person.lastName = null;
        person.gender = null;
        person.birthdate = null;
        person.address = null;
    }

    @Override
    public String toString() {
        if (this.firstName == null) {
            return "Person wurde gelöscht.";
        }

        return "\nPerson " + id +" { " +
                "\n  vorname = " + firstName +
                "\n  nachname = " + lastName +
                "\n  Geschlecht = " + gender +
                "\n  geburtsdatum = " + birthdate +
                "\n  Address = " + address +
                "\n}";
    }
}
