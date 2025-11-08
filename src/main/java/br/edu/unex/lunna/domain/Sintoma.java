package br.edu.unex.lunna.domain;

import br.edu.unex.lunna.domain.enums.Intensidade;
import br.edu.unex.lunna.domain.enums.TipoSintoma;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_sintoma")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Sintoma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataRegistro;

    @Enumerated(EnumType.STRING)
    private TipoSintoma tipo;

    @Enumerated(EnumType.STRING)
    private Intensidade intensidade;

    @ManyToOne
    @JoinColumn(name = "ciclo_menstrual_id")
    private CicloMenstrual cicloMenstrual;
}
