package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.Sintoma;
import br.edu.unex.lunna.domain.CicloMenstrual;
import br.edu.unex.lunna.repository.SintomaRepository;
import br.edu.unex.lunna.repository.CicloMenstrualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoSintoma {

    private final SintomaRepository sintomaRepo;
    private final CicloMenstrualRepository cicloRepo;

    public Sintoma salvar(Long cicloId, Sintoma sintoma) {
        CicloMenstrual ciclo = cicloRepo.findById(cicloId)
                .orElseThrow(() -> new RuntimeException("Ciclo não encontrado"));
        sintoma.setCicloMenstrual(ciclo);
        return sintomaRepo.save(sintoma);
    }

    public List<Sintoma> listarPorCiclo(Long cicloId) {
        return sintomaRepo.findByCicloMenstrualId(cicloId);
    }

    public void deletar(Long id) {
        sintomaRepo.deleteById(id);
    }
}
