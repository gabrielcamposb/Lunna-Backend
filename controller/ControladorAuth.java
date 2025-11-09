package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.dto.LoginDTO;
import br.edu.unex.lunna.dto.RegistroDTO;
import br.edu.unex.lunna.service.ServicoUsuario;
import br.edu.unex.lunna.service.ServicoJwtToken;
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
    private ServicoJwtToken servicoJwtToken;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegistroDTO dto) {
        Usuario usuario = servicoUsuario.registrar(dto);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
            );

            DetalhesUsuario detalhesUsuario =
                    (DetalhesUsuario) servicoUsuario.loadUserByUsername(loginDTO.getEmail());

            String token = servicoJwtToken.gerarToken(
                    detalhesUsuario.getUsername(),
                    detalhesUsuario.getAuthorities().iterator().next().getAuthority()
            );

            return ResponseEntity.ok(Map.of("token", token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
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
