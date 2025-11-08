package br.edu.unex.lunna.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class ConfiguracaoJwt {

    @Bean
    public SecretKey chaveJwt(@Value("${security.jwt.secret}") String segredoBase64) {
        byte[] decodificado = Base64.getDecoder().decode(segredoBase64);
        return new SecretKeySpec(decodificado, "HmacSHA256");
    }

    @Bean
    public JwtEncoder codificadorJwt(SecretKey chave) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(chave.getEncoded()));
    }

    @Bean
    public JwtDecoder decodificadorJwt(SecretKey chave) {
        return NimbusJwtDecoder.withSecretKey(chave).build();
    }
}
