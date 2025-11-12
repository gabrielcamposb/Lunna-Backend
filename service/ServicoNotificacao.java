package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.Notificacao;
import br.edu.unex.lunna.domain.Usuario;
import br.edu.unex.lunna.domain.enums.TipoNotificacao;
import br.edu.unex.lunna.repository.NotificacaoRepository;
import br.edu.unex.lunna.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoNotificacao {

    private final NotificacaoRepository notificacaoRepo;
    private final UsuarioRepository usuarioRepo;
    private final ServicoDadosMenstruais servicoDadosMenstruais;

    public Notificacao criar(Notificacao notificacao, Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        notificacao.setUsuario(usuario);
        return notificacaoRepo.save(notificacao);
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void executarEnvioAgendado() {
        enviarLembretes();
    }

    public List<Notificacao> enviarLembretes() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        List<Notificacao> notificacoesCriadas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            try {
                LocalDate proximoCiclo = servicoDadosMenstruais.preverProximoCiclo(usuario.getId());
                if (proximoCiclo == null) continue;

                LocalDate hoje = LocalDate.now();
                long diasAteProximo = ChronoUnit.DAYS.between(hoje, proximoCiclo);

                String titulo = null;
                String mensagem = null;

                if (diasAteProximo == 0) {
                    titulo = "Início do Ciclo";
                    mensagem = "Seu ciclo menstrual começa hoje. Cuide-se bem e descanse ❤️";
                } else if (diasAteProximo == 1) {
                    titulo = "Amanhã é o início do ciclo";
                    mensagem = "Seu ciclo está previsto para começar amanhã. Prepare-se 💕";
                } else if (diasAteProximo == 14) {
                    titulo = "Período fértil estimado";
                    mensagem = "Você pode estar entrando em seu período fértil 🌸";
                } else if (diasAteProximo < 0 && diasAteProximo >= -5) {
                    titulo = "Fim do ciclo";
                    mensagem = "Seu ciclo terminou recentemente. Lembre-se de se hidratar e descansar 💧";
                }

                if (titulo != null) {
                    boolean jaEnviada = notificacaoRepo.existsByUsuarioIdAndTituloAndDataEnvioBetween(
                            usuario.getId(),
                            titulo,
                            LocalDateTime.now().minusDays(1),
                            LocalDateTime.now()
                    );

                    if (!jaEnviada) {
                        Notificacao notificacao = Notificacao.builder()
                                .titulo(titulo)
                                .mensagem(mensagem)
                                .icone("bell")
                                .dataEnvio(LocalDateTime.now())
                                .tipo(TipoNotificacao.LEMBRETE)
                                .usuario(usuario)
                                .build();

                        notificacaoRepo.save(notificacao);
                        notificacoesCriadas.add(notificacao);
                    }
                }

            } catch (Exception e) {
                System.out.println("⚠️ Erro ao gerar lembrete para usuário " + usuario.getId() + ": " + e.getMessage());
            }
        }

        return notificacoesCriadas;
    }

    public List<Notificacao> listarPorUsuario(Long usuarioId) {
        return notificacaoRepo.findByUsuarioId(usuarioId);
    }
}
