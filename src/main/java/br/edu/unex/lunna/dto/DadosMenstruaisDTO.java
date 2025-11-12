package br.edu.unex.lunna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosMenstruaisDTO {
    private Long id;
    private LocalDate dataInicioCiclo;
    private LocalDate dataFimCiclo;
    private Integer duracaoCicloEmDias;
}
