package br.edu.unex.lunna.domain;

import br.edu.unex.lunna.domain.enums.IntervaloPilula;
import br.edu.unex.lunna.domain.enums.MetodoContraceptivo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_dados_menstruais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DadosMenstruais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicioCiclo;
    private LocalDate dataFimCiclo;
    private Integer duracaoCicloEmDias;
    private LocalDate dataNascimento;
    private boolean usaMetodoContraceptivo;

    @Enumerated(EnumType.STRING)
    private MetodoContraceptivo metodoContraceptivo;

    @Enumerated(EnumType.STRING)
    private IntervaloPilula intervaloPilula;

    private LocalDate dataInicioPilula;
    private LocalDate dataUltimaMenstruacao;

    @Builder.Default
    @OneToMany(mappedBy = "dadosMenstruais", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CicloMenstrual> ciclos = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
