package org.example;

public interface AddressDAO {
    int createAddress(Address address) throws RuntimeException;

    void updateAddress(Address address, String strassenname, int hausnummer, int plz, String ort) throws RuntimeException;

    Address getAddress(int id) throws RuntimeException;

    void deleteAddress(int id) throws RuntimeException;
}
