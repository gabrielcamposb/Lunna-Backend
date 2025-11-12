package br.edu.unex.lunna.repository;

import br.edu.unex.lunna.domain.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndTituloAndDataEnvioBetween(
            Long usuarioId,
            String titulo,
            LocalDateTime inicio,
            LocalDateTime fim
    );
}