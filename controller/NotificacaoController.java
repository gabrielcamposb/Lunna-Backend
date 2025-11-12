package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.domain.Notificacao;
import br.edu.unex.lunna.service.ServicoNotificacao;
import br.edu.unex.lunna.service.DetalhesUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final ServicoNotificacao servicoNotificacao;

    @GetMapping("/listar")
    public ResponseEntity<?> listar(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        DetalhesUsuario detalhes = (DetalhesUsuario) auth.getPrincipal();
        Long usuarioId = detalhes.getUsuario().getId();

        List<Notificacao> notificacoes = servicoNotificacao.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(notificacoes);
    }

    @PostMapping("/forcar-envio")
    public ResponseEntity<?> forcarEnvio() {
        List<Notificacao> criadas = servicoNotificacao.enviarLembretes();
        if (criadas.isEmpty()) {
            return ResponseEntity.ok("Nenhuma nova notificação foi criada agora.");
        }
        return ResponseEntity.ok(criadas);
    }
}
