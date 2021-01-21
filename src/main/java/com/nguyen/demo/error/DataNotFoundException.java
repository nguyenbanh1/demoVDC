package com.nguyen.demo.error;

import lombok.Getter;

@Getter
public class DataNotFoundException extends ApplicationRuntimeException {

    private static final long serialVersionUID = -8797468301279461814L;

    public DataNotFoundException(String message) {
        super(ErrorCode.ERR_NOT_FOUND, "notfound", message, "data");
    }
}
