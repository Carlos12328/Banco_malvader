package banco.malvader.banco_malvader.dto;

public record CreateUsuarioDto(
        String nome,
        String cpf,
        String dataNascimento, // Pode ser String (ISO-8601) ou LocalDate, conforme necessidade
        String telefone,
        String tipoUsuario,
        String senhaHash
) {
}

