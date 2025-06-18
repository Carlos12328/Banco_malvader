package banco.malvader.banco_malvader.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class ClienteController {

    @GetMapping("/clientes")
    public String mostrarCliente() {
        return "cliente";
    }
}
