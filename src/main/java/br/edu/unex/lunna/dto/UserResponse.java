package br.edu.unex.lunna.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String email;
    private String role;
    private String password;

    public UserResponse(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UserResponse(Long id, String email, String role, String password) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
    }
}
