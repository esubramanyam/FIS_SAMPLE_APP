package com.fis.deloitte.planOnboarding.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String email;
    private String city;
    private String contactNo;
    private LocalDateTime lastLoginTime;
    private LocalDateTime updatedDateTime;

}