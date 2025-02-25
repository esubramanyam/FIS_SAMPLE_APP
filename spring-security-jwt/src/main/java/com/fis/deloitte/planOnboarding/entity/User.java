package com.fis.deloitte.planOnboarding.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
@Builder
public class User implements UserDetails {

    @Id
    @TableGenerator(name = "user_id_generator", table = "id_generator", pkColumnName = "gen_name", valueColumnName = "gen_value", pkColumnValue = "user_id", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id_generator")

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotEmpty(message="Please enter name")
    @Size(min=2,max=50,message="Name must be more than 2 characters long")
    private String username;
    //MethodArgumentNotValidException

    @NotEmpty(message="Please enter your password")
    @Size(min=5,message="Password must be more than 5 characters long")
    private String password;

    @Email(message="Invalid email format")
    @NotEmpty(message="Email is required")
    private String email;

    @NotEmpty(message="Please enter city")
    @Size(min=2,max=50,message="City must be more than 2 characters long")
    private String city;

    @NotEmpty(message = "Please enter contact number")
    @Size(min = 10, max = 10, message = "Contact number must be 10 characters long")
    private String contactNo;

    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //  return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        // return UserDetails.super.isEnabled();
        return true;
    }
}
