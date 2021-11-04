package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.models.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
