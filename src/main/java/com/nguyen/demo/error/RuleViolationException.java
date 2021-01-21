package com.nguyen.demo.error;

public class RuleViolationException extends ApplicationRuntimeException {

    private static final long serialVersionUID = -1411725814198858200L;

    public RuleViolationException(String message) {
        super(ErrorCode.ERR_BAD_REQUEST, "badrequest", message, "badrequest");
    }
}
