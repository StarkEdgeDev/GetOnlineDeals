package com.app.getonlinedeals.Features.BillingAddress;

import java.io.Serializable;

public class AddressModel implements Serializable {
    private String city, address, lastName, firstName, email, state, country, pin, phone;

    public AddressModel(String city, String address, String lastName, String firstName, String email, String state,
                        String country, String pin, String phone) {
        this.address = address;
        this.city = city;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.state = state;
        this.country = country;
        this.pin = pin;
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPin() {
        return pin;
    }

    public String getPhone() {
        return phone;
    }
}
