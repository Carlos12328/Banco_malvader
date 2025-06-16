package banco.malvader.banco_malvader.controller.web;

import banco.malvader.banco_malvader.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

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
    public String telaLogin() {
        return "login";
    }

    @PostMapping
    public String autenticar(@RequestParam String cpf,
                         @RequestParam String senha,
                         Model model,
                         HttpSession session) {

    boolean loginValido = usuarioService.verificarLogin(cpf, senha);

    if (loginValido) {
        session.setAttribute("cpfAutenticado", cpf); // Armazena CPF na sessão
        return "redirect:/otp"; // Não precisa mais mandar por URL
    } else {
        model.addAttribute("erro", "Usuário ou senha inválidos");
        return "login";
    }
}

}
