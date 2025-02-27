package org.example;

import java.sql.*;
import java.text.SimpleDateFormat;

public class PersonenDAOImpl implements PersonDAO{

    private static final AddressDAO addressDAO = new AddressDAOImpl();

    @Override
    public int createPerson(String vorname, String nachname) throws RuntimeException {
        String query = "INSERT INTO Persons (Vorname, Nachname) VALUES (?, ?)";
        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, vorname);
            stmt.setString(2, nachname);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Beim INSERT wurde keine Autoincrement-ID generiert");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public int createPerson(String vorname, String nachname, Person.Geschlecht geschlecht, String geburtsdatum, Address address) throws RuntimeException {
        String query = "INSERT INTO Persons (Vorname, Nachname, Geschlecht, Geburtsdatum, address_id) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, vorname);
                    stmt.setString(2, nachname);
                    stmt.setString(3, geschlecht.name());
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
                    java.util.Date parsedDate = inputFormat.parse(geburtsdatum);
                    Date sqlDate = new Date(parsedDate.getTime());
                    stmt.setDate(4, sqlDate);
                    if (address.getId() == 0) {
                        int addressId = addressDAO.createAddress(address);
                        address.setId(addressId); // ID der Adresse aktualisieren
                    }
                    stmt.setInt(5, address.getId());

                    stmt.executeUpdate();

                    ResultSet rs2 = stmt.getGeneratedKeys();
                    if (rs2.next()) {
                        return rs2.getInt(1);
                    } else {
                        throw new SQLException("Beim INSERT wurde keine Autoincrement-ID generiert");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

    }

    @Override
    public int createPerson(String Vorname, String Nachname, Person.Geschlecht geschlecht, String Geburtsdatum) throws RuntimeException {
        String query = "INSERT INTO Persons (Vorname, Nachname, Geschlecht, Geburtsdatum) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, Vorname);
            stmt.setString(2, Nachname);
            stmt.setString(3, geschlecht.name());
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
            java.util.Date parsedDate = inputFormat.parse(Geburtsdatum);
            Date sqlDate = new Date(parsedDate.getTime());
            stmt.setDate(4, sqlDate);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Beim INSERT wurde keine Autoincrement-ID generiert");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person getPerson(int id) throws RuntimeException {
        String query = "Select * from Persons WHERE ID = ?";

        Person newPerson = null;
        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                newPerson = PersonenVerwaltung.getPerson(id);
                if (newPerson == null) {//Vorname, Nachname, Geschlecht, Geburtsdatum, Straßenname, Hausnummer, PLZ, Ort
                    if (rs.getString("Geschlecht") == null) {
                        newPerson = new Person(rs.getInt("ID"), rs.getString("Vorname"), rs.getString("Nachname"));
                    } else if (rs.getString("Straßenname") == null) {
                        Person.Geschlecht newPersonGeschlecht = Person.Geschlecht.valueOf(rs.getString("Geschlecht"));
                        newPerson = new Person(rs.getInt("ID"), rs.getString("Vorname"), rs.getString("Nachname"),  newPersonGeschlecht, rs.getString("Geburtsdatum"));
                    } else {
                        Person.Geschlecht newPersonGeschlecht = Person.Geschlecht.valueOf(rs.getString("Geschlecht"));
                        Address address = new Address(rs.getString("Straßenname"), rs.getInt("Hausnummer"), rs.getInt("PLZ"), rs.getString("Ort"));
                        newPerson = new Person(rs.getInt("ID"), rs.getString("Vorname"), rs.getString("Nachname"),  newPersonGeschlecht, rs.getString("Geburtsdatum"), address);
                    }
                    PersonenVerwaltung.createPerson(newPerson.getId(), newPerson);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newPerson;

    }

    @Override
    public void updatePersonVorname(Person person, String vorname) throws RuntimeException {
        String query = "UPDATE Persons SET Vorname = ? WHERE ID = ?";

        if (vorname == null || vorname.trim().isEmpty()) {
            throw new IllegalArgumentException("Vorname darf nicht leer sein.");
        }

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setString(1, vorname);
            stmt.setInt(2, person.getId());
            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 0) {
                throw new RuntimeException("Keine Person mit der ID " + person.getId() + " gefunden.");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePersonNachname(Person person, String nachname) throws RuntimeException {
        String query = "UPDATE Persons SET Nachname = ? WHERE ID = ?";

        if (nachname == null || nachname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nachname darf nicht leer sein.");
        }

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setString(1, nachname);
            stmt.setInt(2, person.getId());
            int affectedRows = stmt.executeUpdate();

            if(affectedRows == 0) {
                throw new RuntimeException("Keine Person mit der ID " + person.getId() + " gefunden.");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePersonGeschlecht(Person person, Person.Geschlecht geschlecht) throws RuntimeException {
        String query = "UPDATE Persons SET Geschlecht = ? WHERE ID = ?";

        if (geschlecht == null) {
            throw new IllegalArgumentException("Geschlecht darf nicht leer sein.");
        }

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setString(1, geschlecht.name());
            stmt.setInt(2, person.getId());
            int affectedRows = stmt.executeUpdate();

            if(affectedRows == 0) {
                throw new RuntimeException("Keine Person mit der ID " + person.getId() + " gefunden.");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePersonAdresse(Person person, Address address) throws RuntimeException {
        String query = "UPDATE Persons SET address_id = ? WHERE ID = ?";

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            if (address == null) {
                stmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(1, address.getId());
            }
            stmt.setInt(2, person.getId());
            int affectedRows = stmt.executeUpdate();

            if(affectedRows == 0) {
                throw new RuntimeException("Keine Person mit der ID " + person.getId() + " gefunden.");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePersonGeburtsdatum(Person person, String geburtsdatum) throws RuntimeException {
        String query = "UPDATE Persons SET Geburtsdatum = ? WHERE ID = ?";

        if (geburtsdatum == null) {
            throw new IllegalArgumentException("Geschlecht darf nicht leer sein.");
        }

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setString(1, geburtsdatum);
            stmt.setInt(2, person.getId());
            int affectedRows = stmt.executeUpdate();

            if(affectedRows == 0) {
                throw new RuntimeException("Keine Person mit der ID " + person.getId() + " gefunden.");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerson(int id) throws RuntimeException {
        String query = "DELETE FROM Persons WHERE ID = ?";
        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate(); // Korrekte Methode für DELETE
            if (affectedRows == 0) {
                System.out.println("Keine Person mit der ID " + id + " gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
