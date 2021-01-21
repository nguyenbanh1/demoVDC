package com.nguyen.demo.social;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private String id;

    @JsonProperty("first_name")
    private String firstName;
    private String name;
    private String email;

}
