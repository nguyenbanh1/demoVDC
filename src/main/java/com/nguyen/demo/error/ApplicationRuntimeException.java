package com.nguyen.demo.error;

import lombok.Getter;

@Getter
public class ApplicationRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 2777318448600747364L;

    private final ErrorCode errorCode;

    private final Error error;

    /**
     * Constructor .
     *
     * @param errorCode the given errorCode
     * @param error      the given error
     */
    public ApplicationRuntimeException(ErrorCode errorCode, Error error) {
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
    public ApplicationRuntimeException(ErrorCode errorCode,
                                       String code, String message, String target) {
        this.errorCode = errorCode;
        this.error = Error.builder().message(message).code(code).target(target).build();
    }
}
