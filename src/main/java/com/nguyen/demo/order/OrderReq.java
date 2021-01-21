package com.nguyen.demo.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class OrderReq {

    @NotNull
    private String fullName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String email;

    @Size(min = 1)
    private List<OrderProduct> orderProducts;
}
