package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.models.FormOfControl;

public interface FormOfControlRepository extends JpaRepository<FormOfControl, Long> {
}
