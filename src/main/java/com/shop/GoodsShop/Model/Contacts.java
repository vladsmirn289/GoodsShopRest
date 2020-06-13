package com.shop.GoodsShop.Model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class Contacts {
    @NotBlank(message = "Zip code cannot be empty")
    @Size(min = 5, max = 9, message = "Zip code must be between 5 and 9 digits")
    private String zipCode;

    @NotBlank(message = "Country cannot be empty")
    private String country;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "Street cannot be empty")
    private String street;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;


    protected Contacts() {}

    public Contacts(String zipCode,
                    String country,
                    String city,
                    String street,
                    String phoneNumber) {
        this.zipCode = zipCode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.phoneNumber = phoneNumber;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
