package com.nguyen.demo.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * ERR_INVALID_DATA.
     */
    ERR_INVALID_DATA(100, "Input data is not valid."),

    /**
     * ERR_USER_NOT_EXIST.
     */
    ERR_USER_NOT_EXIST(110, "User does not exist"),

    /**
     * ERR_USER_NOT_VALID.
     */
    ERR_USER_NOT_VALID(111, "User name or password is not correct"),

    /**
     * USER_ALREADY_EXIST.
     */
    USER_ALREADY_EXIST(112, "Email already exist"),

    /**
     * USER_PENDING_STATUS.
     */
    USER_PENDING_STATUS(113, "User have not activated"),

    /**
     * INVALID_PARAMETER.
     */
    INVALID_PARAMETER(200, "Invalid request parameter"),

    /**
     * ERR_UNAUTHORIZED.
     */
    ERR_UNAUTHORIZED(401, "Unauthorized or Access Token is expired"),

    /**
     * ERR_PERMISSION_DENIED.
     */
    ERR_PERMISSION_DENIED(402, "Access Permission denied"),


    /**
     * FORBIDDEN.
     */
    FORBIDDEN(403, "Forbidden"),

    /**
     * Common status.
     */
    OK(200, null),

    /**
     * ERR_INTERNAL_SERVER.
     */
    ERR_INTERNAL_SERVER(500, "Internal Error"),

    /**
     * SQL_ERROR.
     */
    SQL_ERROR(501, "SQL Error"),

    /**
     * ERR_BAD_REQUEST.
     */
    ERR_BAD_REQUEST(400, "Bad request"),

    /**
     * ERR_USER_NOT_FOUND.
     */
    ERR_NOT_FOUND(404, "Not Found"),

    /**
     * ERR_METHOD_NOT_ALLOWED.
     */
    ERR_METHOD_NOT_ALLOWED(405, "Method not allowed");


    private final int code;

    private final String message;
}
