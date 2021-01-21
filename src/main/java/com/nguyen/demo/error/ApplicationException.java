package com.nguyen.demo.error;

import lombok.Getter;

@Getter
public class ApplicationException extends Exception {

    private static final long serialVersionUID = 2777318448600747364L;

    private final ErrorCode errorCode;

    private final Error error;

    /**
     * Constructor .
     *
     * @param errorCode the given errorCode
     * @param error      the given error
     */
    public ApplicationException(ErrorCode errorCode, Error error) {
        this.errorCode = errorCode;
        this.error = error;
    }

    /**
     * Constructor .
     *
     * @param errorCode the given errorCode
     * @param code      the given code
     * @param message   the given message
     * @param target    the given message
     */
    public ApplicationException(ErrorCode errorCode,
                                String code, String message, String target) {
        this.errorCode = errorCode;
        this.error = Error.builder().message(message).code(code).target(target).build();
    }
}
