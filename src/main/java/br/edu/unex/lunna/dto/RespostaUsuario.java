package br.edu.unex.lunna.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaUsuario {
    private Long id;
    private String email;
    private String cargo;

    public RespostaUsuario(Long id, String email, String cargo) {
        this.id = id;
        this.email = email;
        this.cargo = cargo;
    }

    public RespostaUsuario(Long id, String email, String cargo, String senha) {
        this.id = id;
        this.email = email;
        this.cargo = cargo;
    }
}
