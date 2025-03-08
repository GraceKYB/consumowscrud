package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Cargo;
import ec.grace.consumows.crud.consumowscrud.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cargo")
public class CargoController {
    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Operation(summary = "Obtener lista de cargos")
    @GetMapping("/listar")
    public Collection<Cargo> listarCargos() {
        return cargoService.listarCargos();
    }

    @Operation(summary = "Guardar un nuevo cargo")
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCargo(@RequestBody Cargo cargo) {
        boolean guardado = cargoService.guardar(cargo);
        if (guardado) {
            return ResponseEntity.ok().body("Cargo guardado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar cargo");
        }
    }

    @Operation(summary = "Actualizar cargo")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCargo(@PathVariable Integer id, @RequestBody Cargo cargo) {
        boolean actualizado = cargoService.actualizar(id, cargo);
        if (actualizado) {
            return ResponseEntity.ok().body("Cargo actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar cargo");
        }
    }

    @Operation(summary = "Eliminar cargo")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCargo(@PathVariable Long id) {
        boolean eliminado = cargoService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok().body("Cargo eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar cargo");
        }
    }
}
