package br.edu.unex.lunna.repository;

import br.edu.unex.lunna.domain.CicloMenstrual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CicloMenstrualRepository extends JpaRepository<CicloMenstrual, Long> {
    List<CicloMenstrual> findByDadosMenstruaisId(Long dadosId);
}