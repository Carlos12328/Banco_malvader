// LoginController.java
package banco.malvader.banco_malvader.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String create() {
        return "login";
    }
}
