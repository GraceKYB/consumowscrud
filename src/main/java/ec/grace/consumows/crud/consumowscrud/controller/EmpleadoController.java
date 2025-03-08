package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Empleado;
import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.service.EmpleadoService;
import ec.grace.consumows.crud.consumowscrud.service.RegistroService;
import ec.grace.consumows.crud.consumowscrud.vo.EmpleadoRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {


    private final EmpleadoService registroService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.registroService = empleadoService;
    }

    @Operation(summary = "Obtener lista de empleados")
    @GetMapping("/listarEmpleados")
    public Collection<Empleado> listarEmpleados() {
        return registroService.listarEmpleados();
    }

    @Operation(summary = "Guardar un nuevo empleado")
    @PostMapping("/guardar")
    public boolean guardar(@RequestBody EmpleadoRequestVo empleado) {
        return registroService.guardar(empleado);
    }

    @Operation(summary = "Eliminar  empleado")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        boolean eliminado = registroService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok().body("Empleado eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar empleado");
        }
    }

    @Operation(summary = "Actualizar empleado")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
        boolean actualizado = registroService.actualizar(id, empleado);
        if (actualizado) {
            return ResponseEntity.ok().body("Empleado actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar empleado");
        }
    }
}
