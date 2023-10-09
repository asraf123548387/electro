package com.shopping.electroshopping.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
   @Getter
   @Setter
   @NoArgsConstructor
   public class ProductDto {
      private String ProductName;
      private double price;
      private String description;
      private Long categoryId;

      private String imageName;
   private Integer stock;



}
