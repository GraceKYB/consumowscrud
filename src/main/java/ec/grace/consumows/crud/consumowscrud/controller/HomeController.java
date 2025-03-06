package ec.grace.consumows.crud.consumowscrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/api/home")
public class HomeController {

    @GetMapping("/index")
    public String index(Model model) {
        // Título inicial de la tabla
        model.addAttribute("tableTitle", "Cargo");
        return "index";
    }
}