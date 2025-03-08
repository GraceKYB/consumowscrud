package ec.grace.consumows.crud.consumowscrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaCargoController {
    @GetMapping("/cargo")
    public String mostrarCargo(Model model) {
        return "cargo"; // Debe haber un cargo.html en src/main/resources/templates/
    }
}
