package com.nguyen.demo.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
    private List<OrderProduct> orderProducts;
    private Long totalPrice;
}
