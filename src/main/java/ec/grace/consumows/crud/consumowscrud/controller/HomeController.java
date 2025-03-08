package ec.grace.consumows.crud.consumowscrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index(@RequestParam(required = false) String cedula,
                        @RequestParam(required = false) String nombre,
                        @RequestParam(required = false) String correo,
                        Model model) {
        model.addAttribute("cedula", cedula);
        model.addAttribute("nombre", nombre);
        model.addAttribute("correo", correo);
        return "home";
    }

}