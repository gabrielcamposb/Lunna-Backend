package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.CicloMenstrual;
import br.edu.unex.lunna.domain.DadosMenstruais;
import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.domain.enums.FaseMenstrual;
import br.edu.unex.lunna.domain.enums.IntervaloPilula;
import br.edu.unex.lunna.domain.enums.MetodoContraceptivo;
import br.edu.unex.lunna.dto.DadosMenstruaisDTO;
import br.edu.unex.lunna.repository.CicloMenstrualRepository;
import br.edu.unex.lunna.repository.DadosMenstruaisRepository;
import br.edu.unex.lunna.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoDadosMenstruais {

    private final DadosMenstruaisRepository dadosRepo;
    private final UsuarioRepository usuarioRepo;
    private final CicloMenstrualRepository cicloRepo;

    @Transactional
    public DadosMenstruais salvar(Long usuarioId, DadosMenstruaisDTO dto) {

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        DadosMenstruais dados = new DadosMenstruais();
        dados.setDataInicioCiclo(dto.dataInicioCiclo());
        dados.setDataFimCiclo(dto.dataFimCiclo());
        dados.setDuracaoCicloEmDias(dto.duracaoCicloEmDias());
        dados.setUsaMetodoContraceptivo(dto.usaMetodoContraceptivo());
        dados.setDataInicioPilula(dto.dataInicioPilula());
        dados.setDataUltimaMenstruacao(dto.dataUltimaMenstruacao());

        if (dto.metodoContraceptivo() != null) {
            try {
                MetodoContraceptivo met = MetodoContraceptivo.valueOf(dto.metodoContraceptivo());
                dados.setMetodoContraceptivo(met);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("MetodoContraceptivo inválido: " + dto.metodoContraceptivo());
            }
        }

        if (dto.intervaloPilula() != null) {
            try {
                IntervaloPilula intervalo = IntervaloPilula.valueOf(dto.intervaloPilula());
                dados.setIntervaloPilula(intervalo);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("IntervaloPilula inválido: " + dto.intervaloPilula());
            }
        }

        dados.setUsuario(usuario);

        DadosMenstruais salvo = dadosRepo.save(dados);

        CicloMenstrual ciclo = CicloMenstrual.builder()
                .dataInicio(dto.dataInicioCiclo())
                .duracaoDias(dto.duracaoCicloEmDias() != null ? dto.duracaoCicloEmDias() : 28)
                .faseAtual(FaseMenstrual.MENSTRUACAO)
                .dadosMenstruais(salvo)
                .build();

        cicloRepo.save(ciclo);

        return salvo;
    }

    public List<DadosMenstruais> listarPorUsuario(Long usuarioId) {
        return dadosRepo.findByUsuarioId(usuarioId);
    }

    public void deletar(Long id) {
        dadosRepo.deleteById(id);
    }

    public LocalDate preverProximoCiclo(Long usuarioId) {
        List<DadosMenstruais> dados = listarPorUsuario(usuarioId);
        if (dados.isEmpty()) throw new RuntimeException("Nenhum ciclo cadastrado.");

        double mediaDuracao = dados.stream()
                .mapToInt(d -> d.getDuracaoCicloEmDias() != null ? d.getDuracaoCicloEmDias() : 28)
                .average()
                .orElse(28);

        DadosMenstruais ultimo = dados.get(dados.size() - 1);
        return ultimo.getDataInicioCiclo().plusDays((long) Math.round(mediaDuracao));
    }
}
