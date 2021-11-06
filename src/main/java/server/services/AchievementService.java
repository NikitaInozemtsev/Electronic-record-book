package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.dto.AchievementDto;
import server.filters.AchievementFilter;
import server.models.Achievement;
import server.repositories.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementService {

    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    FormOfControlRepository formOfControlRepository;
    @Autowired
    DisciplineRepository disciplineRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private StudentRepository studentRepository;

    public List<Achievement> findAll(AchievementFilter filter) {
        return achievementRepository.findAll(byFilter(filter));
    }

    public boolean createAchievement(AchievementDto achievementDto) {

        try {
            Achievement achievement = Achievement.builder()
                    .student(studentRepository.findById(achievementDto.getStudentId()).get())
                    .professor(professorRepository.findById(achievementDto.getProfessorId()).get())
                    .formOfControl(formOfControlRepository.findById(achievementDto.getFormOfControlId()).get())
                    .discipline(disciplineRepository.findById(achievementDto.getDisciplineId()).get())
                    .mark(achievementDto.getMark())
                    .semester(achievementDto.getSemester())
                    .date(achievementDto.getDate())
                    .build();
            achievementRepository.save(achievement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            achievementRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer deleteByIds(List<Long> ids) {
        try {
            List<Achievement> achievements = achievementRepository.findAllById(ids);
            achievementRepository.deleteAll(achievements);
            return achievements.size();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Integer deleteByFilter(AchievementFilter filter) {
        try {
            List<Achievement> achievements = achievementRepository.findAll(byFilter(filter));
            achievementRepository.deleteAll(achievements);
            return achievements.size();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Specification<Achievement> byFilter(AchievementFilter filter) {
        return new Specification<Achievement>() {
            @Override
            public Predicate toPredicate(Root<Achievement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (filter.getDateFrom() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("date"), filter.getDateFrom()));
                }
                if (filter.getDateTo() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("date"), filter.getDateTo()
                    ));
                }
                if (filter.getStudentId() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("student"), filter.getStudentId()
                    ));
                }
                if (filter.getGroupId() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("student").get("group"), filter.getGroupId()
                    ));
                }
                if (filter.getProfessorId() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("professor"), filter.getProfessorId()
                    ));
                }
                if (filter.getFormOfControlId() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("formOfControl"), filter.getFormOfControlId()
                    ));
                }
                if (filter.getSemester() != null) {
                    predicates.add(criteriaBuilder.equal(
                            root.get("semester"), filter.getSemester()
                    ));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
