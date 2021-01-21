package com.nguyen.demo.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private List<String> roles;
}
