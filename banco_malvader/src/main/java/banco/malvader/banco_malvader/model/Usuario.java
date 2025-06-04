package banco.malvader.banco_malvader.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private int idUsuario;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome pode ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 dígitos")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 15, message = "O telefone pode ter no máximo 15 caracteres")
    private String telefone;

    @NotNull(message = "O tipo de usuário é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 32, message = "A senha deve ter entre 6 e 32 caracteres")
    @Column(name = "senha_hash", nullable = false, length = 32)
    private String senhaHash;

    @Size(max = 6, message = "O OTP deve ter no máximo 6 dígitos")
    @Column(name = "otp_ativo", length = 6)
    private String otpAtivo;

    @Column(name = "otp_expiracao")
    private LocalDateTime otpExpiracao;
}
