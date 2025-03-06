package ec.grace.consumows.crud.consumowscrud.controller;

import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.service.RegistroService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
       this.registroService = registroService;
   }
//
//    @Operation(summary = "Obtener todos los registros")
//    @GetMapping("/obtenerTodos")
//    public List<Logss> obtenerTodos() {
//        return registroService.obtenerTodos();
//    }
//
//    @Operation(summary = "Guardar un nuevo registro")
//    @PostMapping("/guardar")
//    public Logss guardar(@RequestBody Logss registro) {
//        return registroService.guardar(registro);
//    }
@GetMapping
public List<Logs> getAllLogss() {
    return registroService.getAllLogs();
}

    @GetMapping("/{id}")
    public ResponseEntity<Logs> getLogsById(@PathVariable Long id) {
        return registroService.getLogsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cedula/{cedula}")
    public List<Logs> getLogssByCedula(@PathVariable String cedula) {
        return registroService.getLogssByCedula(cedula);
    }

    @PostMapping
    public Logs createLogs(@RequestBody Logs Logs) {
        return registroService.saveLogs(Logs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogs(@PathVariable Long id) {
        registroService.deleteLogs(id);
        return ResponseEntity.noContent().build();
    }
}
