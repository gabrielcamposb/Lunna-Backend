package br.edu.unex.lunna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DadosMenstruaisResumoDTO {
    private Long id;
    private LocalDate dataInicioCiclo;
    private LocalDate dataFimCiclo;
    private Integer duracaoCicloEmDias;
}
