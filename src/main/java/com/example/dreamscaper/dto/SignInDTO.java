package com.example.dreamscaper.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInDTO {
    @NotEmpty(message = "This field cannot be empty")
    private String username;
    @NotEmpty(message = "This field cannot be empty")
    private String password;

}
