package ec.grace.consumows.crud.consumowscrud.repository;

import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistroRepository extends JpaRepository<Logs, Long> {
    List<Logs> findByCedula(String cedula);
    List<Logs> findByEstado(String estado);
}
