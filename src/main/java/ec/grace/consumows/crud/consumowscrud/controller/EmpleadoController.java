package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.service.EmpleadoService;
import ec.grace.consumows.crud.consumowscrud.service.RegistroService;
import ec.grace.consumows.crud.consumowscrud.vo.EmpleadoRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {

    private final EmpleadoService registroService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.registroService = empleadoService;
    }

//    @Operation(summary = "Obtener todos los empleados")
//    @GetMapping("/obtenerTodos")
//    public List<Logs> obtenerTodos() {
//        return registroService.obtenerTodos();
//    }

    @Operation(summary = "Guardar un nuevo empleado")
    @PostMapping("/guardar")
    public boolean guardar(@RequestBody EmpleadoRequestVo empleado) {
        return registroService.guardar(empleado);
    }
}
