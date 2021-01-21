package com.nguyen.demo.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Field error.
 */
@Data
public class FieldError implements Serializable {

    private static final long serialVersionUID = 2341356998185008763L;

    @JsonProperty("object_name")
    private final String objectName;

    private final String target;

    private final String message;

    private final String code;
}
