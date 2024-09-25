package com.dev.eCommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "email is required.")
    private String email;

    @NotBlank(message = "pw is required.")
    private String password;

}
