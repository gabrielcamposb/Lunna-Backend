package br.edu.unex.lunna.mapper;

import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.dto.RespostaUsuario;
import org.springframework.stereotype.Component;

@Component
public class MapeadorUsuario {
    public RespostaUsuario paraDto(Usuario usuario) {
        if (usuario == null)
            return null;

        return new RespostaUsuario(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCargo().name()
        );
    }
}
