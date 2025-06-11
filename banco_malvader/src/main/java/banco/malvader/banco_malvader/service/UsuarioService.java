package banco.malvader.banco_malvader.service;

import banco.malvader.banco_malvader.dto.*;
import banco.malvader.banco_malvader.exception.UsuarioNotFoundException;
import banco.malvader.banco_malvader.model.*;
import banco.malvader.banco_malvader.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// Importando classes do Spring Security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; // Repositório usado para acessar dados da tabela 'usuario'
    private final PasswordEncoder passwordEncoder;      // Responsável por criptografar e verificar senhas

    @PersistenceContext
    private EntityManager entityManager; // Usado para executar consultas/manipulações nativas no banco


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Instancia usada para codificar/verificar senhas
    }

    public Integer createUsuario(CreateUsuarioDto createUsuarioDto) {
    var entity = new Usuario(); // Cria um novo objeto Usuario
    entity.setNome(createUsuarioDto.nome());         // Define o nome
    entity.setCpf(createUsuarioDto.cpf());           // Define o CPF
    entity.setDataNascimento(LocalDate.parse(createUsuarioDto.dataNascimento())); // Converte e define data
    entity.setTelefone(createUsuarioDto.telefone()); // Define telefone
    entity.setTipoUsuario(TipoUsuario.valueOf(createUsuarioDto.tipoUsuario())); // Enum (CLIENTE ou FUNCIONARIO)

    // Criptografa a senha antes de salvar no banco
    String senhaCriptografada = passwordEncoder.encode(createUsuarioDto.senha());
    entity.setSenhaHash(senhaCriptografada);

    var usuarioSaved = usuarioRepository.save(entity); // Salva no banco
    return usuarioSaved.getIdUsuario();                // Retorna o ID gerado
    }

    public Usuario getUsuarioByCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário com CPF " + cpf + " não encontrado."));
    }

    public List<Usuario> listUsuarios() {
        return usuarioRepository.findAll();
    }

    public void updateUsuarioByCpf(String cpf, UpdateUsuarioDto updateUsuarioDto) {
        var usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário com CPF " + cpf + " não encontrado."));

        if (updateUsuarioDto.nome() != null) {
            usuario.setNome(updateUsuarioDto.nome());
        }

        if (updateUsuarioDto.telefone() != null) {
            usuario.setTelefone(updateUsuarioDto.telefone());
        }

        if (updateUsuarioDto.senhaHash() != null) {
            // Criptografa nova senha antes de salvar
            String novaSenhaHash = passwordEncoder.encode(updateUsuarioDto.senhaHash());
            usuario.setSenhaHash(novaSenhaHash);
        }

        usuarioRepository.save(usuario);
    }

    public void deleteByCpf(String cpf) {
        var usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário com CPF " + cpf + " não encontrado."));

        usuarioRepository.delete(usuario);
    }

    public boolean verificarLogin(String cpf, String senha) {
    Optional<Usuario> user = usuarioRepository.findByCpf(cpf);

    if (user.isPresent() && passwordEncoder.matches(senha, user.get().getSenhaHash())) {
        // Login válido: gera OTP
        gerarOtpParaUsuario(user.get().getIdUsuario());
        return true;
    }

    return false;
    }

    public String gerarOtpParaUsuario(Integer idUsuario) {
        var query = entityManager
            .createNativeQuery("CALL gerar_otp(:id_usuario)")
            .setParameter("id_usuario", idUsuario);

        var resultado = query.getSingleResult(); // procedure retorna o otp

        return resultado != null ? resultado.toString() : null;
    }
    
    public boolean validarOtp(String cpf, String otpInformado) {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
            .orElseThrow(() -> new UsuarioNotFoundException("Usuário com CPF " + cpf + " não encontrado."));

        // Verifica se o OTP está preenchido e ainda não expirou
        if (usuario.getOtpAtivo() == null || usuario.getOtpExpiracao() == null) {
            return false;
        }

        boolean otpCorreto = usuario.getOtpAtivo().equals(otpInformado);
        boolean naoExpirou = usuario.getOtpExpiracao().isAfter(LocalDateTime.now());

        return otpCorreto && naoExpirou;
    }


}
