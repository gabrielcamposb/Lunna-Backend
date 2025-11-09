package br.edu.unex.lunna.domain;

import br.edu.unex.lunna.domain.enums.TipoNotificacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notificacao")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensagem;
    private String icone;

    private LocalDateTime dataEnvio;

    @Enumerated(EnumType.STRING)
    private TipoNotificacao tipo;
}
