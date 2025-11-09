package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.domain.DadosMenstruais;
import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.repository.UsuarioRepository;
import br.edu.unex.lunna.service.ServicoDadosMenstruais;
import br.edu.unex.lunna.service.ServicoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ciclos")
@RequiredArgsConstructor
public class ControladorCicloMenstrual {

    private final ServicoDadosMenstruais servicoDadosMenstruais;
    private final ServicoUsuario servicoUsuario;
    private final UsuarioRepository usuarioRepo;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody DadosMenstruais dados, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(403).body("Usuário não autenticado");
        }

        String email = authentication.getName();
        Usuario usuario = servicoUsuario.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        DadosMenstruais salvo = servicoDadosMenstruais.salvar(usuario.getId(), dados);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/listar")
    public List<DadosMenstruais> listar(@AuthenticationPrincipal Usuario usuario) {
        return servicoDadosMenstruais.listarPorUsuario(usuario.getId());
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        servicoDadosMenstruais.deletar(id);
    }

    @GetMapping("/proximo")
    public LocalDate proximoCiclo(@AuthenticationPrincipal Usuario usuario) {
        return servicoDadosMenstruais.preverProximoCiclo(usuario.getId());
    }

}
