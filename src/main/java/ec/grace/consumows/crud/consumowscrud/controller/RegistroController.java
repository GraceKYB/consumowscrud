package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.service.RegistroService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @Operation(summary = "Obtener todos los registros")
    @GetMapping("/obtenerTodos")
    public List<Logs> obtenerTodos() {
        return registroService.obtenerTodos();
    }

    @Operation(summary = "Guardar un nuevo registro")
    @PostMapping("/guardar")
    public Logs guardar(@RequestBody Logs registro) {
        return registroService.guardar(registro);
    }
}
