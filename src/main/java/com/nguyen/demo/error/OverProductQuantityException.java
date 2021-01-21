package com.nguyen.demo.error;

public class OverProductQuantityException extends ApplicationRuntimeException {

    private static final long serialVersionUID = -3928284527199080046L;

    public OverProductQuantityException(String message) {
        super(ErrorCode.ERR_BAD_REQUEST, "overproductquantity", message, "data");
    }

}
