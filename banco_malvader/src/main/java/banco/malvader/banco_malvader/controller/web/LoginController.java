package banco.malvader.banco_malvader.controller.web;

import banco.malvader.banco_malvader.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login") // Rota base da página de login
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mostra a tela de login (GET /login)
    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    // Processa o formulário de login (POST /login)
    @PostMapping
    public String autenticar(@RequestParam String cpf,
                             @RequestParam String senha,
                             Model model) {

        boolean loginValido = usuarioService.verificarLogin(cpf, senha);

        if (loginValido) {
            return "redirect:/home"; // redireciona para a próxima tela
        } else {
            model.addAttribute("erro", "Usuário ou senha inválidos");
            return "login"; // recarrega a página com mensagem de erro
        }
    }
}
