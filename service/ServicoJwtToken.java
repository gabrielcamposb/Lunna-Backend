package br.edu.unex.lunna.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServicoJwtToken {

    private static final String CHAVE_SECRETA = "minhaChaveSuperSecretaParaJWTDePeloMenos32Caracteres!";
    private final Key chaveAssinatura = Keys.hmacShaKeyFor(CHAVE_SECRETA.getBytes());

    public String gerarToken(String username, String cargo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("cargo", cargo);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(chaveAssinatura)
                .compact();
    }

    public String extrairUsername(String token) {
        return extrairClaims(token).getPayload().getSubject();
    }

    public boolean validarToken(String token, String usernameEsperado) {
        String usernameToken = extrairUsername(token);
        return (usernameToken.equals(usernameEsperado) && !estaExpirado(token));
    }

    private boolean estaExpirado(String token) {
        return extrairClaims(token).getPayload().getExpiration().before(new Date());
    }

    private Jws<Claims> extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) chaveAssinatura)
                .build()
                .parseSignedClaims(token);
    }
}
