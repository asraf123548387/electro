package com.shopping.electroshopping.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDtoPaypal {
    private double price;
    private String currency;
    private String method;
    private String intend;
    private String description;

}
