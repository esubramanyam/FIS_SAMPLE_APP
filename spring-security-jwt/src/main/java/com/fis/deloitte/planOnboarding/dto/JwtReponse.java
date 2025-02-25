package com.fis.deloitte.planOnboarding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtReponse {
    private String jwtToken;
    private String username;
    private LocalDateTime lastLoginTime;
    private LocalDateTime updatedDateTime;
}
