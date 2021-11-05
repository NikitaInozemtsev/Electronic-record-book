package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {


}
