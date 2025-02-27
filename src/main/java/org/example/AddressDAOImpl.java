package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressDAOImpl implements AddressDAO {

    @Override
    public int createAddress(Address address) throws RuntimeException {
        String query = "INSERT INTO Addresses (Streetname, Housenumber, PLZ, City) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, address.getStreetname());
            stmt.setInt(2, address.getHousenumber());
            stmt.setInt(3, address.getPlz());
            stmt.setString(4, address.getCity());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Beim INSERT wurde keine Autoincrement-ID generiert");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAddress(Address address, String strassenname, int Housenumber, int plz, String city) throws RuntimeException {
        String query = "UPDATE Addresses SET Streetname = ?, Housenumber = ?, PLZ = ?, City = ? WHERE ID = ?";

        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setString(1, strassenname);
            stmt.setInt(2, Housenumber);
            stmt.setInt(3, plz);
            stmt.setString(4, city);
            stmt.setInt(5, address.getId());
            

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Keine Address mit der ID " + address.getId() + " gefunden.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Address getAddress(int id) throws RuntimeException {
        String query = "Select * from Addresses WHERE ID = ?";

        Address address = null;
        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                address = PersonenVerwaltung.getAddress(id);
                address = new Address(rs.getString("Streetname"), rs.getInt("Housenumber"), rs.getInt("plz"), rs.getString("city"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AddressID " + id + " wurde nicht gefunden.", e);
        }
        return address;
    }

    @Override
    public void deleteAddress(int id) throws RuntimeException {
        String query = "DELETE FROM Addresses WHERE ID = ?";
        try (PreparedStatement stmt = DBConnector.getInstance().prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate(); // Korrekte Methode für DELETE
            if (affectedRows == 0) {
                System.out.println("Keine Adresse mit der ID " + id + " gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
