package br.edu.unex.lunna.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequisicaoAuth {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String senha;
}
