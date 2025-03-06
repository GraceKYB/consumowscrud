package ec.grace.consumows.crud.consumowscrud.service;
import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.repository.RegistroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;

    public RegistroService(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public List<Logs> obtenerTodos() {
        return registroRepository.findAll();
    }

    public Logs guardar(Logs registro) {
        return registroRepository.save(registro);
    }
    public List<Logs> getAllLogs() {
        return registroRepository.findAll();
    }
    public Optional<Logs> getLogsById(Long id) {
        return registroRepository.findById(id);
    }

    public List<Logs> getLogssByCedula(String cedula) {
        return registroRepository.findByCedula(cedula);
    }

    public Logs saveLogs(Logs Logs) {
        return registroRepository.save(Logs);
    }

    public void deleteLogs(Long id) {
        registroRepository.deleteById(id);
    }
}
