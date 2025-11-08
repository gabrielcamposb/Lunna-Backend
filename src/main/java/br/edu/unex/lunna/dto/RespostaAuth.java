package br.edu.unex.lunna.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaAuth {
    private String tokenAcesso;
    private String tipoToken = "Bearer";
    private long expiraEm;

    public RespostaAuth(String tokenAcesso, long expiraEm) {
        this.tokenAcesso = tokenAcesso;
        this.expiraEm = expiraEm;
    }
}
