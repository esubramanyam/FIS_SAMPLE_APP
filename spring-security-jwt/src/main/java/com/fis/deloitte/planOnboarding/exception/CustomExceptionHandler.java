package com.fis.deloitte.planOnboarding.exception;

import com.fis.deloitte.planOnboarding.constants.ExceptionConstants;
import com.fis.deloitte.planOnboarding.dto.ErrorDetail;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail>  handleBadCredentialException(BadCredentialsException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_UNAUTHORIZED, ExceptionConstants.ERROR_MESSAGE_BAD_CREDENTIALS,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(401));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail>  handleAccessDeniedException(AccessDeniedException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_UNAUTHORIZED, ExceptionConstants.ERROR_MESSAGE_ACCESS_DENIED,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(401));

    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorDetail>  handleSignatureException(SignatureException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_JWT_SIGNATURE,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorDetail>  handleExpiredJwtException(ExpiredJwtException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_EXPIRED_JWT_TOKEN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetail>  handleIllegalArgumentException(IllegalArgumentException ex) {
         ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_ARGUMENT,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorDetail>  handleMalformedJwtException(MalformedJwtException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_TOKEN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(403));
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ErrorDetail>  handleInvalidHeaderException(InvalidHeaderException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_INVALID_HEADER, ExceptionConstants.ERROR_MESSAGE_INVALID_HEADER_VALUE,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(301));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorDetail>  handleAuthException(AuthException ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_FORBIDDEN, ExceptionConstants.ERROR_MESSAGE_INVALID_TOKEN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(401));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail>  handleUnknownException(Exception ex) {
        ErrorDetail errorDetail = new ErrorDetail(ExceptionConstants.ERROR_CODE_UNKNOWN, ExceptionConstants.ERROR_MESSAGE_UNKNOWN,ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(302));
    }
}