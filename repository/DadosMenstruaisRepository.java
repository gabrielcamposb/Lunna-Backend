package br.edu.unex.lunna.repository;

import br.edu.unex.lunna.domain.DadosMenstruais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DadosMenstruaisRepository extends JpaRepository<DadosMenstruais, Long> {
    List<DadosMenstruais> findByUsuarioId(Long usuarioId);
}
