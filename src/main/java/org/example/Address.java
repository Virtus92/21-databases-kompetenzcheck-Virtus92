package org.example;

public class Address {
    private int id;
    private String streetname;
    private int housenumber;
    private int plz;
    private String city;
    private static final AddressDAO addressDAO = new AddressDAOImpl();

    //Konstuktor
    public Address(String streetname, int housenumber, int PLZ, String city) {
        this.streetname = streetname;
        this.housenumber = housenumber;
        this.plz = PLZ;
        this.city = city;
        this.id = PersonenVerwaltung.createAddress(this);
    }


    //Abrufen
    public int getId() {
        return this.id;
    }

    public String getStreetname() {
        return streetname;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public int getPlz() {
        return plz;
    }

    public String getCity() {
        return city;
    }

    public static Address getAddress(int id) {
        return addressDAO.getAddress(id);
//        Address savedAddress = PersonenVerwaltung.getAddress(id);
//        Address DBAddress = addressDAO.getAddress(id);
//
//        if (savedAddress != null && savedAddress.equals(DBAddress)) {
//            return savedAddress;
//        } else if (savedAddress == null && DBAddress != null) {
//            PersonenVerwaltung.createAddress(DBAddress.id, DBAddress);
//            return DBAddress;
//        } else {
//            System.out.println("Fehler, Addresse wurde nicht gefunden. Null wird verwendet.");
//            return null;
//        }
    }

    //Verändern
    public void updateAddress(String streetname, int housenumber, int plz, String city) {
        addressDAO.updateAddress(this, streetname, housenumber, plz, city);
        this.streetname = streetname;
        this.housenumber = housenumber;
        this.plz = plz;
        this.city = city;
    }

    //Löschen
    public static void deleteAddress(Address address) {
        PersonenVerwaltung.deleteAddress(address);
        address.id = -1;
        address.streetname = null;
        address.housenumber = -1;
        address.plz = -1;
        address.city = null;
    }

    @Override
    public String toString() {
        return streetname + " " + housenumber + ", " + plz + " " + city;
    }

    public void setId(int addressId) {
        this.id = addressId;
    }
}
