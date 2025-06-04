package banco.malvader.banco_malvader.dto;

public record UpdateUsuarioDto(
        String nome,
        String telefone,
        String senhaHash
) {
}
