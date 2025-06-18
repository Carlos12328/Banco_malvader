package banco.malvader.banco_malvader.controller.web;

import banco.malvader.banco_malvader.dto.CreateUsuarioDto;
import banco.malvader.banco_malvader.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class FuncionarioController {

    private final UsuarioService usuarioService;

    public FuncionarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Página de formulário
    @GetMapping("/adicionar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new CreateUsuarioDto(null, null, null, null, null, null));
        model.addAttribute("mensagemSucesso", null);  // Limpa a mensagem de sucesso ao carregar a página
        return "usuario-adicionar"; // Nome do arquivo HTML em /templates
    }

    // Processa o formulário
    @PostMapping("/adicionar")
    public String adicionarUsuario(@ModelAttribute CreateUsuarioDto usuarioDto, Model model) {
        try {
            // Cria o usuário
            int usuarioId = usuarioService.createUsuario(usuarioDto);

            // Define a mensagem de sucesso
            model.addAttribute("mensagemSucesso", "Usuário criado com sucesso!" + usuarioId);
            model.addAttribute("usuario", new CreateUsuarioDto(null, null, null, null, null, null));  // Reseta o formulário

        } catch (Exception e) {
            // Em caso de erro, define a mensagem de erro
            model.addAttribute("erro", "Erro ao cadastrar usuário: " + e.getMessage());
        }

        return "usuario-adicionar"; // Retorna para a mesma página (não redireciona)
    }
}
