package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.DadosMenstruais;
import br.edu.unex.lunna.domain.Usuario;
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

    public DadosMenstruais salvar(Long usuarioId, DadosMenstruais dados) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        dados.setUsuario(usuario);
        return dadosRepo.save(dados);
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

        DadosMenstruais ultimo = dados.get(dados.size() - 1);

        if (ultimo.getDataInicioCiclo() == null || ultimo.getDuracaoCicloEmDias() == null) {
            throw new RuntimeException("Dados incompletos para prever o próximo ciclo.");
        }

        return ultimo.getDataInicioCiclo().plusDays(ultimo.getDuracaoCicloEmDias());
    }
}
