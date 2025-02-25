package com.fis.deloitte.planOnboarding.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtRequest {

    private String username;
    private String password;

}
//validation for input
