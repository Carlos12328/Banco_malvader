package banco.malvader.banco_malvader.controller.api;

import banco.malvader.banco_malvader.dto.CreateUsuarioDto;
import banco.malvader.banco_malvader.dto.UpdateUsuarioDto;
import banco.malvader.banco_malvader.model.Usuario;
import banco.malvader.banco_malvader.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Void> createUsuario(@RequestBody CreateUsuarioDto createUsuarioDto) {
        Integer usuarioId = usuarioService.createUsuario(createUsuarioDto);
        return ResponseEntity.created(URI.create("/v1/usuarios/" + usuarioId)).build();
    }

    @GetMapping("/usuarios/{cpf}")
    public ResponseEntity<Usuario> getUsuarioByCpf(@PathVariable("cpf") String cpf) {
        Usuario usuario = usuarioService.getUsuarioByCpf(cpf);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listUsuarios() {
        var usuarios = usuarioService.listUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/usuarios/{cpf}")
    public ResponseEntity<Map<String, String>> updateUsuarioByCpf(@PathVariable("cpf") String cpf,
                                                                  @RequestBody UpdateUsuarioDto updateUsuarioDto) {
        usuarioService.updateUsuarioByCpf(cpf, updateUsuarioDto);

        // Criando um corpo de resposta com mensagem
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", "Usuário com CPF " + cpf + " atualizado com sucesso.");

        return ResponseEntity.ok(resposta); // Retorna HTTP 200 com JSON na resposta
    }


    @DeleteMapping("/usuarios/{cpf}")
    public ResponseEntity<Map<String, String>> deleteByCpf(@PathVariable("cpf") String cpf) {
        usuarioService.deleteByCpf(cpf);

        Map<String, String> respostaDelete = new HashMap<>();
        respostaDelete.put("mensagem", "Usuário com CPF " + cpf + " deletado com sucesso.");

        return ResponseEntity.ok(respostaDelete); // Retorna JSON com código 200 e mensagem
    }

}
