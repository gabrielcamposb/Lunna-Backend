package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.CicloMenstrual;
import br.edu.unex.lunna.domain.DadosMenstruais;
import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.domain.enums.FaseMenstrual;
import br.edu.unex.lunna.repository.CicloMenstrualRepository;
import br.edu.unex.lunna.repository.DadosMenstruaisRepository;
import br.edu.unex.lunna.repository.UsuarioRepository;
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

    public DadosMenstruais salvar(Long usuarioId, DadosMenstruais dados) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        dados.setUsuario(usuario);

        DadosMenstruais salvo = dadosRepo.save(dados);

        CicloMenstrual ciclo = CicloMenstrual.builder()
                .dataInicio(dados.getDataInicioCiclo())
                .duracaoDias(
                        dados.getDuracaoCicloEmDias() != null ?
                                dados.getDuracaoCicloEmDias() : 28
                )
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
                .mapToInt(DadosMenstruais::getDuracaoCicloEmDias)
                .average()
                .orElse(28);

        DadosMenstruais ultimo = dados.get(dados.size() - 1);
        return ultimo.getDataInicioCiclo().plusDays((long) mediaDuracao);
    }
}
