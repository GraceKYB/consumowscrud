package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Empresa;
import ec.grace.consumows.crud.consumowscrud.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @Operation(summary = "Obtener lista de empresa")
    @GetMapping("/listar")
    public Collection<Empresa> listarEmpresa() {
        return empresaService.listarEmpresa();
    }

    @Operation(summary = "Guardar un nueva empresa")
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarEmpresa(@RequestBody Empresa empresa) {
        boolean guardado = empresaService.guardar(empresa);
        if (guardado) {
            return ResponseEntity.ok().body("Empresa guardado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar empresa");
        }
    }

    @Operation(summary = "Actualizar empresa")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpresa(@PathVariable Integer id, @RequestBody Empresa empresa) {
        boolean actualizado = empresaService.actualizar(id, empresa);
        if (actualizado) {
            return ResponseEntity.ok().body("Empresa actualizado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar empresa");
        }
    }

    @Operation(summary = "Eliminar empresa")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpresa(@PathVariable Long id) {
        boolean eliminado = empresaService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok().body("Empresa eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar empresa");
        }
    }
}
