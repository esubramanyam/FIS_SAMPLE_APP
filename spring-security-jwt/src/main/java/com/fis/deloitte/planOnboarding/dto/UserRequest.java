package com.fis.deloitte.planOnboarding.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRequest {

    @NotEmpty(message="Please enter name")
    @Size(min=2,max=50,message="Name must be more than 2 characters long")
    private String username;

    @NotEmpty(message="Please enter your password")
    @Size(min=5,max=15,message="Password must be more than 5 characters long")
    private String password;

    @Email(message="Invalid email format")
    @NotEmpty(message="Email is required")
    private String email;

    @NotEmpty(message="Please enter city")
    @Size(min=2,max=50,message="City must be more than 2 characters long")
    private String city;

    @NotEmpty(message = "Please enter contact number")
    @Size(min = 10, max = 10, message = "Contact number must not be 10 characters long")
    private String contactNo;
}
