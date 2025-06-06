package banco.malvader.banco_malvader.service;

import banco.malvader.banco_malvader.dto.*;
import banco.malvader.banco_malvader.exception.UsuarioNotFoundException;
import banco.malvader.banco_malvader.model.*;
import banco.malvader.banco_malvader.repository.UsuarioRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Integer createUsuario(CreateUsuarioDto createUsuarioDto) {
        var entity = new Usuario();
        entity.setNome(createUsuarioDto.nome());
        entity.setCpf(createUsuarioDto.cpf());
        entity.setDataNascimento(LocalDate.parse(createUsuarioDto.dataNascimento()));
        entity.setTelefone(createUsuarioDto.telefone());
        entity.setTipoUsuario(TipoUsuario.valueOf(createUsuarioDto.tipoUsuario()));
        entity.setSenhaHash(createUsuarioDto.senhaHash());

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
            usuario.setSenhaHash(updateUsuarioDto.senhaHash());
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

        return user.isPresent() && user.get().getSenhaHash().equals(senha);
    }

}
