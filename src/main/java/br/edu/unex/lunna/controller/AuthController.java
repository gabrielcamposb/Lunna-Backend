package br.edu.unex.lunna.controller;

import br.edu.unex.lunna.dto.AuthRequest;
import br.edu.unex.lunna.dto.AuthResponse;
import br.edu.unex.lunna.dto.RegisterRequest;
import br.edu.unex.lunna.dto.UserResponse;
import br.edu.unex.lunna.mapper.UserMapper;
import br.edu.unex.lunna.service.JwtTokenService;
import br.edu.unex.lunna.service.UserDetailsImpl;
import br.edu.unex.lunna.service.UserService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtTokenService jwtTokenService;
    private final UserMapper userMapper;

    public AuthController(UserService userService,
                          AuthenticationManager authManager,
                          JwtTokenService jwtTokenService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtTokenService = jwtTokenService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest req) {
        var user = userService.register(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) throws JOSEException {
        var authToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        var auth = authManager.authenticate(authToken);
        var principal = (UserDetailsImpl) auth.getPrincipal();

        String token = jwtTokenService.generate(principal.getUsername(), principal.getUser().getRole().name());
        return ResponseEntity.ok(new AuthResponse(token, jwtTokenService.getTtlSeconds()));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getSubject();
        var user = userService.findByEmail(email);
        UserResponse response = userMapper.toDto(user);
        return ResponseEntity.ok(response);
    }
}
