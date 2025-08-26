package br.edu.unex.lunna.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 72)
    private String password;
}
