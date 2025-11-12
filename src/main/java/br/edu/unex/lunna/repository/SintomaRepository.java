package br.edu.unex.lunna.repository;

import br.edu.unex.lunna.domain.Sintoma;
import br.edu.unex.lunna.domain.enums.TipoSintoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SintomaRepository extends JpaRepository<Sintoma, Long> {
    List<Sintoma> findByCicloMenstrualId(Long cicloId);

    boolean existsByCicloMenstrualIdAndTipoAndDataRegistro(Long cicloId, TipoSintoma tipo, LocalDate dataRegistro);
}