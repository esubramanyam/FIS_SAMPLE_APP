package com.fis.deloitte.planOnboarding.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserException extends RuntimeException{
    private String message;
}
