package com.fis.deloitte.planOnboarding.constants;

public class ExceptionConstants {

    public static final String ERROR_CODE_UNAUTHORIZED = "401";
    public static final String ERROR_CODE_FORBIDDEN = "403";
    public static final String ERROR_CODE_INVALID_HEADER = "301";
    public static final String ERROR_CODE_UNKNOWN = "302";

    public static final String ERROR_MESSAGE_BAD_CREDENTIALS = "Incorrect Username/Password!! Authentication Failure";
    public static final String ERROR_MESSAGE_ACCESS_DENIED = "Not Authorized";
    public static final String ERROR_MESSAGE_INVALID_JWT_SIGNATURE= "JWT Signature not valid!!";
    public static final String ERROR_MESSAGE_EXPIRED_JWT_TOKEN= "JWT Token already expired!!";
    public static final String ERROR_MESSAGE_INVALID_ARGUMENT= "Illegal Argument while fetching the username!!";
    public static final String ERROR_MESSAGE_INVALID_TOKEN= "Invalid Token!!";
    public static final String ERROR_MESSAGE_UNKNOWN ="Unknown Exception occurred!!";
    public static final String ERROR_MESSAGE_INVALID_HEADER_VALUE= "Invalid Header value!!";
}
