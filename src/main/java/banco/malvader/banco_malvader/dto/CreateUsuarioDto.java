package banco.malvader.banco_malvader.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUsuarioDto(
        String nome,
        String cpf,
        String dataNascimento, // formato ISO-8601, ex: "2000-01-01"
        String telefone,
        String tipoUsuario,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 32, message = "A senha deve ter entre 6 e 32 caracteres")
        String senha
) {
}
