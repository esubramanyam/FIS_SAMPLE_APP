package com.fis.deloitte.planOnboarding.constants;

public class ExceptionConstants {

    public static final String ERROR_CODE_UNAUTHORIZED = "401";
    public static final String ERROR_CODE_FORBIDDEN = "403";
    public static final String ERROR_CODE_INVALID_HEADER = "406";
    public static final String ERROR_CODE_UNKNOWN = "407";
    public static final String ERROR_CODE_USER_DETAILS = "408";
    public static final String ERROR_CODE_USERNAME_ALREADY_EXISTS = "409";
    public static final String ERROR_CODE_EMAIL_ALREADY_EXISTS = "410";
    public static final String ERROR_CODE_BAD_REQUEST = "411";
    public static final String ERROR_CODE_USERNAME_TOKEN_FETCH = "412";
    public static final String ERROR_CODE_USER_CREATION = "413";
    public static final String ERROR_CODE_USER_NOT_FOUND= "414";

    public static final String ERROR_MESSAGE_BAD_CREDENTIALS = "Incorrect Username/Password!! Authentication Failure";
    public static final String ERROR_MESSAGE_ACCESS_DENIED = "Not Authorized";
    public static final String ERROR_MESSAGE_INVALID_JWT_SIGNATURE= "JWT Signature not valid!!";
    public static final String ERROR_MESSAGE_EXPIRED_JWT_TOKEN= "JWT Token already expired!!";
    public static final String ERROR_MESSAGE_INVALID_ARGUMENT= "Illegal Argument while fetching the username!!";
    public static final String ERROR_MESSAGE_INVALID_TOKEN= "Invalid Token!!";
    public static final String ERROR_MESSAGE_UNKNOWN ="Unknown Exception occurred!!";
    public static final String ERROR_MESSAGE_INVALID_HEADER_VALUE= "Invalid Header value!!";
    public static final String ERROR_MESSAGE_USER_DETAILS= "Error while fetching users!!";
    public static final String ERROR_MESSAGE_USERNAME_ALREADY_EXISTS= "Username already exists!!";
    public static final String ERROR_MESSAGE_EMAIL_ALREADY_EXISTS= "Email already exists!!";
    public static final String ERROR_MESSAGE_BAD_REQUEST= "Bad Request!!";
    public static final String ERROR_MESSAGE_USERNAME_TOKEN_FETCH= "Error while extracting username from token";
    public static final String ERROR_MESSAGE_USER_CREATION= "Error while creating  user!!";
    public static final String ERROR_MESSAGE_USER_NOT_FOUND= "User not found!!";
}
