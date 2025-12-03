package br.edu.unex.lunna.domain;

import br.edu.unex.lunna.domain.enums.FaseMenstrual;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_ciclo_menstrual")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Data
public class CicloMenstrual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicio;

    private Integer duracaoDias;

    @Enumerated(EnumType.STRING)
    private FaseMenstrual faseAtual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dados_menstruais_id", nullable = false)
    private DadosMenstruais dadosMenstruais;

    @Builder.Default
    @OneToMany(mappedBy = "cicloMenstrual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sintoma> sintomas = new ArrayList<>();
}
