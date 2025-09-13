package br.edu.unex.lunna.mapper;

import br.edu.unex.lunna.domain.User;
import br.edu.unex.lunna.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toDto(User user) {
        if (user == null)
            return null;

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getPassword()
        );
    }
}
