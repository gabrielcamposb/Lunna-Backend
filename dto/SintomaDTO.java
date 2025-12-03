package br.edu.unex.lunna.dto;

import br.edu.unex.lunna.domain.enums.Intensidade;
import br.edu.unex.lunna.domain.enums.TipoSintoma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SintomaDTO {
    private Long id;
    private LocalDate dataRegistro;
    private TipoSintoma tipo;
    private Intensidade intensidade;
    private Long cicloId;
}
