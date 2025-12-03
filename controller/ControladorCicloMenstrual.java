package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.domain.DadosMenstruais;
import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.dto.DadosMenstruaisDTO;
import br.edu.unex.lunna.dto.DadosMenstruaisResumoDTO;
import br.edu.unex.lunna.service.ServicoDadosMenstruais;
import br.edu.unex.lunna.service.ServicoUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ciclos")
@RequiredArgsConstructor
public class ControladorCicloMenstrual {

    private final ServicoDadosMenstruais servicoDadosMenstruais;
    private final ServicoUsuario servicoUsuario;

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody DadosMenstruaisDTO dto, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(403).body("Usuário não autenticado");
        }

        String email = authentication.getName();
        Usuario usuario = servicoUsuario.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        DadosMenstruais salvo = servicoDadosMenstruais.salvar(usuario.getId(), dto);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        String email = authentication.getName();
        Usuario usuario = servicoUsuario.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<DadosMenstruaisResumoDTO> dadosDTO = servicoDadosMenstruais.listarPorUsuario(usuario.getId())
                .stream()
                .map(d -> new DadosMenstruaisResumoDTO(
                        d.getId(),
                        d.getDataInicioCiclo(),
                        d.getDataFimCiclo(),
                        d.getDuracaoCicloEmDias()
                ))
                .toList();

        return ResponseEntity.ok(dadosDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        servicoDadosMenstruais.deletar(id);
        return ResponseEntity.ok("Ciclo deletado com sucesso");
    }

    @GetMapping("/proximo")
    public ResponseEntity<?> proximoCiclo(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        String email = authentication.getName();
        Usuario usuario = servicoUsuario.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        LocalDate proximoCiclo = servicoDadosMenstruais.preverProximoCiclo(usuario.getId());
        return ResponseEntity.ok(proximoCiclo);
    }
}
