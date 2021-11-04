package server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.models.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
