package com.fis.deloitte.planOnboarding.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtReponse {
    private String jwtToken;
    private String username;
}
