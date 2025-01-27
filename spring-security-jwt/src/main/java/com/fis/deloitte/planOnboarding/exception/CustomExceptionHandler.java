package com.fis.deloitte.planOnboarding.exception;

import com.fis.deloitte.planOnboarding.constants.ExceptionConstants;
import com.fis.deloitte.planOnboarding.dto.ErrorDetail;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail>  handleBadCredentialException(BadCredentialsException ex) {
        logger.error("BadCredentialsException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_UNAUTHORIZED, ExceptionConstants.ERROR_MESSAGE_BAD_CREDENTIALS,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(401));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail>  handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("AccessDeniedException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_UNAUTHORIZED, ExceptionConstants.ERROR_MESSAGE_ACCESS_DENIED,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(401));

    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorDetail>  handleSignatureException(SignatureException ex) {
        logger.error("SignatureException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_JWT_SIGNATURE,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDetail>  handleExpiredJwtException(ExpiredJwtException ex) {
        logger.error("ExpiredJwtException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_EXPIRED_JWT_TOKEN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetail>  handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage(), ex);
         ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_ARGUMENT,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorDetail>  handleMalformedJwtException(MalformedJwtException ex) {
        logger.error("MalformedJwtException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_TOKEN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ErrorDetail>  handleInvalidHeaderException(InvalidHeaderException ex) {
        logger.error("InvalidHeaderException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_INVALID_HEADER, ExceptionConstants.ERROR_MESSAGE_INVALID_HEADER_VALUE,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(406));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDetail>  handleAuthException(AuthException ex) {
        logger.error("AuthException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_TOKEN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail>  handleUserException(UserException ex) {
        logger.error("UserException while fetching details: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_USER_DETAILS, ExceptionConstants.ERROR_MESSAGE_USER_DETAILS,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(408));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorDetail> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        logger.error("UsernameAlreadyExistsException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_USERNAME_ALREADY_EXISTS, ExceptionConstants.ERROR_MESSAGE_USERNAME_ALREADY_EXISTS,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(409));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDetail> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        logger.error("EmailAlreadyExistsException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_EMAIL_ALREADY_EXISTS ,ExceptionConstants.ERROR_MESSAGE_EMAIL_ALREADY_EXISTS,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(410));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("MethodArgumentNotValidException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_BAD_REQUEST ,ExceptionConstants.ERROR_MESSAGE_BAD_REQUEST,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(411));
    }
    //UserNameFetchException
    @ExceptionHandler(UserNameFetchException.class)
    public ResponseEntity<ErrorDetail> handleUserNameFetchException(UserNameFetchException ex) {
        logger.error("UserNameFetchException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_USERNAME_TOKEN_FETCH,ExceptionConstants.ERROR_MESSAGE_USERNAME_TOKEN_FETCH,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(412));
    }
    //USER_CREATION_EXCEPTION
    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ErrorDetail> handleUserCreationException(UserCreationException ex) {
        logger.error("UserCreationException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_USER_CREATION ,ExceptionConstants.ERROR_MESSAGE_USER_CREATION,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(413));
    }

    //UserNotFoundException
    @ExceptionHandler({UserNotFoundException.class ,InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorDetail> handleUserNotFoundException(Exception ex) {
        logger.error("UserNotFoundException: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_USER_NOT_FOUND ,ExceptionConstants.ERROR_MESSAGE_USER_NOT_FOUND,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(414));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>  handleUnknownException(Exception ex) {
        logger.error("Unknown Exception: {}", ex.getMessage(), ex);
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_UNKNOWN, ExceptionConstants.ERROR_MESSAGE_UNKNOWN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(407));
    }

}