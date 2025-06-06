package com.spring.foodManagement.Dtos;

import com.spring.foodManagement.Enums.Role;
import jakarta.validation.constraints.*;

public class UserCreateDto {

    @NotBlank(message = "you should enter name")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @Email
    @NotBlank(message = "you should enter email")
    @Size(min = 11, max = 50, message = "email must be between 11 and 50 characters")
    private String email;

    @NotNull(message = "you should enter your role (CUSTOMER / CHEF)")
    private Role role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
