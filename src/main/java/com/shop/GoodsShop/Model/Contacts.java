package com.shop.GoodsShop.Model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
public class Contacts {
    @NotBlank(message = "Zip code doesn't be empty")
    @Size(min = 5, max = 9, message = "Zip code must be between 5 and 9 digits")
    private String zipCode;

    @NotBlank(message = "Address doesn't be empty")
    private String address;

    @NotBlank(message = "Phone number doesn't be empty")
    private String phoneNumber;


    protected Contacts() {}

    public Contacts(String zipCode,
                    String address,
                    String phoneNumber) {
        this.zipCode = zipCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
