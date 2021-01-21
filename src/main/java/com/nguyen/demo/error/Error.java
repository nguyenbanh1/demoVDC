package com.nguyen.demo.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Error.
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error implements Serializable {

    private static final long serialVersionUID = -8000927591085016308L;

    private String code;
    private String message;
    private String target;
    private List<FieldError> details;
}
