package com.fis.deloitte.planOnboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

    private String errorCode;
    private String errorMessage;
    private String errorDesc;
}
