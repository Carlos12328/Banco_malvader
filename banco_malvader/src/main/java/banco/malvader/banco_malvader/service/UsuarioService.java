package banco.malvader.banco_malvader.service;

import banco.malvader.banco_malvader.dto.*;
import banco.malvader.banco_malvader.exception.UsuarioNotFoundException;
import banco.malvader.banco_malvader.model.*;
import banco.malvader.banco_malvader.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

// Importando classes do Spring Security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // Instancia usada para codificar/verificar senhas
    }

    // Criação de usuário com senha criptografada
    public Integer createUsuario(CreateUsuarioDto createUsuarioDto) {
        var entity = new Usuario();
        entity.setNome(createUsuarioDto.nome());
        entity.setCpf(createUsuarioDto.cpf());
        entity.setDataNascimento(LocalDate.parse(createUsuarioDto.dataNascimento()));
        entity.setTelefone(createUsuarioDto.telefone());
        entity.setTipoUsuario(TipoUsuario.valueOf(createUsuarioDto.tipoUsuario()));

        // Criptografando a senha recebida
        String senhaCriptografada = passwordEncoder.encode(createUsuarioDto.senha());
        entity.setSenhaHash(senhaCriptografada);

        var usuarioSaved = usuarioRepository.save(entity);
        return usuarioSaved.getIdUsuario();
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

    // Verificação de login usando o método matches
    public boolean verificarLogin(String cpf, String senha) {
        Optional<Usuario> user = usuarioRepository.findByCpf(cpf);

        // Retorna true se o usuário existir e a senha informada bater com o hash
        return user.isPresent() && passwordEncoder.matches(senha, user.get().getSenhaHash());
    }

}
