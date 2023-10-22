package com.shopping.electroshopping.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesReportDTO {
    private String productName;
    private int quantitySold;
    private double totalSales;
    private int totalQuantitySold;
//    private List<OrderSummaryDTO> orderSummaries;
private Long orderId;
    private LocalDate orderDate;
    private double orderTotal;
}
