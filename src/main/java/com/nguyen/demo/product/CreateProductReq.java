package com.nguyen.demo.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateProductReq {

    @NotNull
    private String name;
    private Long price;
    private String brand;
    private String colour;
    private Integer quantity;
}
