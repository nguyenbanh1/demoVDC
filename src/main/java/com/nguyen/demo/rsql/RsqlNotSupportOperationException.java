package com.nguyen.demo.rsql;

/**
 * Rsql-Specification not support operation exception.
 */
public class RsqlNotSupportOperationException extends RuntimeException {

    private static final long serialVersionUID = -3090360245866971535L;

    /**
     * Constructor for {@link RsqlNotSupportOperationException}.
     */
    public RsqlNotSupportOperationException() {
        super("Rsql not support operation!");
    }
}
