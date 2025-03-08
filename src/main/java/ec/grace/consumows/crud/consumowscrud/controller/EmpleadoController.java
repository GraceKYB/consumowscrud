package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Empleado;
import ec.grace.consumows.crud.consumowscrud.service.EmpleadoService;
import ec.grace.consumows.crud.consumowscrud.vo.EmpleadoRequestVo;
import ec.grace.consumows.crud.consumowscrud.vo.UsuarioSesion;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {

    private final EmpleadoService registroService;

    private UsuarioSesion userSesion;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.registroService = empleadoService;
    }

    @Operation(summary = "Obtener lista de empleados")
    @GetMapping("/listarEmpleados")
    public Collection<Empleado> listarEmpleados() {
        return registroService.listarEmpleados(userSesion);
    }

    @Operation(summary = "Guardar un nuevo empleado")
    @PostMapping("/guardar")
    public boolean guardar(@RequestBody EmpleadoRequestVo empleado) {
        return registroService.guardar(empleado, userSesion);
    }

    @Operation(summary = "Eliminar  empleado")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Integer id) {
        boolean eliminado = registroService.eliminar(id, userSesion);
        if (eliminado) {
            return ResponseEntity.ok().body("Empleado eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar empleado");
        }
    }

    @Operation(summary = "Actualizar empleado")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Integer id, @RequestBody Empleado empleado) {
        boolean actualizado = registroService.actualizar(id, empleado, userSesion);
        if (actualizado) {
            return ResponseEntity.ok().body("Empleado actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar empleado");
        }
    }


    @PostMapping("/guardarUsuarioSesion")
    @ResponseBody
    public ResponseEntity<String> guardarUsuarioSesion(@RequestBody Map<String, String> body) {
        userSesion = new UsuarioSesion();
        userSesion.setCedula(body.get("cedula"));
        userSesion.setNombre(body.get("nombre"));
        EmpresaController.userSesion =userSesion;
        CargoController.userSesion =userSesion;
        return ResponseEntity.ok("Guardado Usuario Sesion");
    }
}
