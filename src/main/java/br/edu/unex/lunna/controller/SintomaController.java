package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.domain.Sintoma;
import br.edu.unex.lunna.dto.SintomaDTO;
import br.edu.unex.lunna.service.ServicoSintoma;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sintomas")
@RequiredArgsConstructor
public class SintomaController {

    private final ServicoSintoma servicoSintoma;

    @PostMapping("/adicionar/{cicloId}")
    public ResponseEntity<?> adicionar(@PathVariable Long cicloId,
                                       @RequestBody Sintoma sintoma,
                                       Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        Sintoma salvo = servicoSintoma.salvar(cicloId, sintoma);

        SintomaDTO dto = SintomaDTO.builder()
                .id(salvo.getId())
                .dataRegistro(salvo.getDataRegistro())
                .tipo(salvo.getTipo())
                .intensidade(salvo.getIntensidade())
                .cicloId(salvo.getCicloMenstrual().getId())
                .build();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/listar/{cicloId}")
    public ResponseEntity<?> listar(@PathVariable Long cicloId, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        List<Sintoma> sintomas = servicoSintoma.listarPorCiclo(cicloId);
        return ResponseEntity.ok(sintomas);
    }

    @GetMapping("/resumo/{cicloId}")
    public ResponseEntity<?> resumo(@PathVariable Long cicloId, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        String resumo = servicoSintoma.gerarResumoSintomas(cicloId);
        return ResponseEntity.ok(resumo);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
        servicoSintoma.deletar(id);
        return ResponseEntity.ok("Sintoma deletado");
    }
}
