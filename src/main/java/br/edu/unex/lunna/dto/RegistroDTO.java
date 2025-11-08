package br.edu.unex.lunna.dto;

import br.edu.unex.lunna.domain.enums.Cargo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistroDTO {
    private String nome;
    private String email;
    private String apelido;
    private String senha;
    private Cargo cargo;

    public RegistroDTO() {}

    public RegistroDTO(String nome, String email, String apelido, String senha, Cargo cargo) {
        this.nome = nome;
        this.email = email;
        this.apelido = apelido;
        this.senha = senha;
        this.cargo = cargo;
    }

}
