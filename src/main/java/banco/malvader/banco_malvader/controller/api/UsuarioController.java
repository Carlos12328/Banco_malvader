package banco.malvader.banco_malvader.controller.api;

import banco.malvader.banco_malvader.dto.*;
import banco.malvader.banco_malvader.model.Usuario;
import banco.malvader.banco_malvader.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios") // Prefixo REST padrão
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> createUsuario(@RequestBody CreateUsuarioDto createUsuarioDto) {
        Integer usuarioId = usuarioService.createUsuario(createUsuarioDto);
        return ResponseEntity.created(URI.create("/api/v1/usuarios/" + usuarioId)).build();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Usuario> getUsuarioByCpf(@PathVariable("cpf") String cpf) {
        Usuario usuario = usuarioService.getUsuarioByCpf(cpf);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listUsuarios() {
        var usuarios = usuarioService.listUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Map<String, String>> updateUsuarioByCpf(@PathVariable("cpf") String cpf,
                                                                  @RequestBody UpdateUsuarioDto updateUsuarioDto) {
        usuarioService.updateUsuarioByCpf(cpf, updateUsuarioDto);

        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Usuário com CPF " + cpf + " atualizado com sucesso.");

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Map<String, String>> deleteByCpf(@PathVariable("cpf") String cpf) {
        usuarioService.deleteByCpf(cpf);

        Map<String, String> respostaDelete = new HashMap<>();
        respostaDelete.put("mensagem", "Usuário com CPF " + cpf + " deletado com sucesso.");

        return ResponseEntity.ok(respostaDelete);
    }

    @PostMapping("/validar_otp")
    public ResponseEntity<String> validarOtp(@RequestBody ValidarOtpDto dto) {
    boolean valido = usuarioService.validarOtp(dto.cpf(), dto.otp());

    if (valido) {
        return ResponseEntity.ok("OTP válido. Acesso liberado.");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP inválido ou expirado.");
    }
}

}
