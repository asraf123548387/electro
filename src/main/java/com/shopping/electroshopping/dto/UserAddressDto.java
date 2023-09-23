package com.shopping.electroshopping.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDto {

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setStreetDetails(String streetDetails) {
        this.streetDetails = streetDetails;
    }

    private  Long user_id;
    @Getter
    private String streetDetails;
    @Getter
    private String cityName;
    private String city;
    private String state;
    private String postalCode;
    private String addressPhoneNumber;


}
