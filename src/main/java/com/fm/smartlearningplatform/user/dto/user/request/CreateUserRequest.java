package com.fm.smartlearningplatform.user.dto.user.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserRequest(

        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.",regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String email,

        @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits.")
        String phoneNumber,

        @NotBlank(message = "Password is required.")
        String password


) {
        public CreateUserRequest{
                if(email != null){
                        email = email.trim().toLowerCase();
                }
                if(password != null){
                        password = password.toLowerCase();
                }
        }
}
