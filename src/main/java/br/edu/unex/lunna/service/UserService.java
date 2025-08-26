package br.edu.unex.lunna.service;

import br.edu.unex.lunna.domain.Role;
import br.edu.unex.lunna.domain.User;
import br.edu.unex.lunna.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new UserDetailsImpl(user);
    }

    @Transactional
    public User register(String email, String rawPassword) {
        if (repo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        var user = new User(null, email, passwordEncoder.encode(rawPassword), Role.USER);
        return repo.save(user);
    }
}
