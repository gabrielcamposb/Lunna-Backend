package br.edu.unex.lunna.mapper;

import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.dto.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MapeadorUsuario {

    public UsuarioResponseDTO paraDto(Usuario usuario) {
        if (usuario == null)
            return null;

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .apelido(usuario.getApelido())
                .email(usuario.getEmail())
                .cargo(usuario.getCargo())
                .build();
    }
}
