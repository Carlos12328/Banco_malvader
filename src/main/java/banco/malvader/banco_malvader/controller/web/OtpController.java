package banco.malvader.banco_malvader.controller.web;

import banco.malvader.banco_malvader.model.Usuario;
import banco.malvader.banco_malvader.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/otp")
public class OtpController {

    private final UsuarioService usuarioService;

    public OtpController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String exibirFormularioOtp(HttpSession session, Model model) {
        String cpf = (String) session.getAttribute("cpfAutenticado");

        if (cpf == null) {
            return "redirect:/login"; // Se não fez login
        }

        model.addAttribute("cpf", cpf);
        return "otp";
    }

    @PostMapping
    public String verificarOtp(HttpSession session, @RequestParam("otp") String otp, Model model) {

        String cpf = (String) session.getAttribute("cpfAutenticado");

        if (cpf == null) {
            return "redirect:/login";
        }

        boolean valido = usuarioService.validarOtp(cpf, otp);

        if (!valido) {
            model.addAttribute("erro", "OTP inválido ou expirado.");
            model.addAttribute("cpf", cpf);
            return "otp";
        }

        Usuario usuario = usuarioService.getUsuarioByCpf(cpf);

        if (usuario == null) {
            model.addAttribute("erro", "Usuário não encontrado.");
            return "login";
        }

        // Limpa sessão após validação
        session.removeAttribute("cpfAutenticado");

        String tipo = usuario.getTipoUsuario().name();

        // Redirecionamento conforme tipo
        if ("FUNCIONARIO".equalsIgnoreCase(tipo)) {
            return "redirect:/usuarios/adicionar"; // Vai para o formulário
        } else if ("CLIENTE".equalsIgnoreCase(tipo)) {
            return "redirect:/usuarios/clientes"; // Altere se tiver página de cliente
        } else {
            model.addAttribute("erro", "Tipo de usuário não autorizado.");
            return "login";
        }
    }
}
