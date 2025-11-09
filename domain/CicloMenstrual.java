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
public class CicloMenstrual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicio;

    private int duracaoDias;

    @Enumerated(EnumType.STRING)
    private FaseMenstrual faseAtual;

    @ManyToOne
    @JoinColumn(name = "dados_menstruais_id")
    private DadosMenstruais dadosMenstruais;

    @Builder.Default
    @OneToMany(mappedBy = "cicloMenstrual", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sintoma> sintomas = new ArrayList<>();
}
