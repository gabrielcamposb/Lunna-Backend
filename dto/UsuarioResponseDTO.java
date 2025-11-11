package br.edu.unex.lunna.dto;

import br.edu.unex.lunna.domain.enums.Cargo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String apelido;
    private String email;
    private Cargo cargo;
}
