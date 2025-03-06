package ec.grace.consumows.crud.consumowscrud.repository;

import ec.grace.consumows.crud.consumowscrud.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Logs, Long> {
}
