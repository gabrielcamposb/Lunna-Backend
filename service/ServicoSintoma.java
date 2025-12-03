package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.CicloMenstrual;
import br.edu.unex.lunna.domain.Sintoma;
import br.edu.unex.lunna.domain.enums.Intensidade;
import br.edu.unex.lunna.domain.enums.TipoSintoma;
import br.edu.unex.lunna.repository.CicloMenstrualRepository;
import br.edu.unex.lunna.repository.SintomaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoSintoma {

    private final SintomaRepository sintomaRepo;
    private final CicloMenstrualRepository cicloRepo;

    public Sintoma salvar(Long cicloId, Sintoma sintoma) {
        CicloMenstrual ciclo = cicloRepo.findById(cicloId)
                .orElseThrow(() -> new RuntimeException("Ciclo não encontrado"));

        sintoma.setCicloMenstrual(ciclo);
        if (sintoma.getDataRegistro() == null) {
            sintoma.setDataRegistro(LocalDate.now());
        }

        return sintomaRepo.save(sintoma);
    }

    public List<Sintoma> listarPorCiclo(Long cicloId) {
        return sintomaRepo.findByCicloMenstrualId(cicloId);
    }

    public boolean existeSintomaHoje(Long cicloId, TipoSintoma tipo) {
        return sintomaRepo.existsByCicloMenstrualIdAndTipoAndDataRegistro(
                cicloId,
                tipo,
                LocalDate.now()
        );
    }

    public void deletar(Long id) {
        sintomaRepo.deleteById(id);
    }

    public String gerarResumoSintomas(Long cicloId) {
        List<Sintoma> sintomas = sintomaRepo.findByCicloMenstrualId(cicloId);
        if (sintomas.isEmpty()) {
            return "Nenhum sintoma registrado neste ciclo.";
        }

        var agrupados = sintomas.stream()
                .collect(Collectors.groupingBy(Sintoma::getTipo, Collectors.counting()));

        StringBuilder resumo = new StringBuilder("Resumo de sintomas:\n");
        agrupados.forEach((tipo, qtd) ->
                resumo.append("• ").append(tipo).append(": ").append(qtd).append(" vezes\n"));

        List<Sintoma> intensos = sintomas.stream()
                .filter(s -> s.getIntensidade() == Intensidade.FORTE)
                .toList();

        if (!intensos.isEmpty()) {
            resumo.append("\nSintomas intensos detectados:\n");
            intensos.forEach(s ->
                    resumo.append("⚠️ ").append(s.getTipo())
                            .append(" em ").append(s.getDataRegistro())
                            .append("\n"));
        }

        return resumo.toString();
    }
}
