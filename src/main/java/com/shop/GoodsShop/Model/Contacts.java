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
}
