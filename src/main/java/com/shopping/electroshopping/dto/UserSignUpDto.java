package com.shopping.electroshopping.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter

@NoArgsConstructor
public class UserSignUpDto {

    @NotNull(message = "user name is empty")
    private String userName;
    @Email(message = "not a valid email")
    private String email;
    @NotNull(message = "password is empty")
    private String password;
    @NotNull(message = "phone number is empty")
    private String phoneNumber;
    public UserSignUpDto(String userName, String email, String password,String phoneNumber) {
        super();
        this.userName = userName;

        this.email = email;
        this.password = password;
        this.phoneNumber=phoneNumber;
    }

}
