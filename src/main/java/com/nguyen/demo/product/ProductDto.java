package com.nguyen.demo.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private String brand;
    private String colour;
}
