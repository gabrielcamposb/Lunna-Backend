package br.edu.unex.lunna.dto;

import java.time.LocalDate;

public record DadosMenstruaisDTO(
        LocalDate dataInicioCiclo,
        LocalDate dataFimCiclo,
        Integer duracaoCicloEmDias,
        Boolean usaMetodoContraceptivo,
        String metodoContraceptivo,
        String intervaloPilula,
        LocalDate dataInicioPilula,
        LocalDate dataUltimaMenstruacao,
        Long usuarioId
) {}
