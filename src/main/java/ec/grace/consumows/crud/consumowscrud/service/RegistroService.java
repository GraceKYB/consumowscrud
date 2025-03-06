package ec.grace.consumows.crud.consumowscrud.service;
import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import ec.grace.consumows.crud.consumowscrud.repository.RegistroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
