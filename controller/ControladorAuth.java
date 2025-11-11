package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.config.JwtService;
import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.dto.RequisicaoAuth;
import br.edu.unex.lunna.dto.RequisicaoRegistro;
import br.edu.unex.lunna.service.ServicoUsuario;
import br.edu.unex.lunna.service.DetalhesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class ControladorAuth {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ServicoUsuario servicoUsuario;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RequisicaoRegistro dto) {
        Usuario usuario = servicoUsuario.registrar(dto);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequisicaoAuth loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );

            DetalhesUsuario detalhesUsuario =
                    (DetalhesUsuario) servicoUsuario.loadUserByUsername(loginDTO.getEmail());

            String token = jwtService.gerarToken(detalhesUsuario);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "email", detalhesUsuario.getUsername(),
                    "cargo", detalhesUsuario.getAuthorities().iterator().next().getAuthority()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas!");
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> obterUsuarioAutenticado(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        DetalhesUsuario detalhes = (DetalhesUsuario) authentication.getPrincipal();
        return ResponseEntity.ok(detalhes.getUsuario());
    }

}
