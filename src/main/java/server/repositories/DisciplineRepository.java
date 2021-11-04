package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.models.Discipline;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
