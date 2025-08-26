package br.edu.unex.lunna.service;

import com.nimbusds.jose.JOSEException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtTokenService {
    private final JwtEncoder encoder;

    @Getter
    private final long ttlSeconds;

    private final String issuer;

    public JwtTokenService(JwtEncoder encoder,
                           @Value("${security.jwt.issuer}") String issuer,
                           @Value("${security.jwt.ttl-seconds}") long ttlSeconds) {
        this.encoder = encoder;
        this.issuer = issuer;
        this.ttlSeconds = ttlSeconds;
    }

    public String generate(String subject, String role) throws JOSEException {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ttlSeconds))
                .subject(subject)
                .claim("scope", "USER")
                .claim("role", role)
                .build();

        JwsHeader header = JwsHeader.with(() -> "HS256").build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
