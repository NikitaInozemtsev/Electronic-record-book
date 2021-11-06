package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.filters.AchievementFilter;
import server.models.Achievement;
import server.repositories.AchievementRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementService {

    @Autowired
    private AchievementRepository repository;

    public List<Achievement> findAll(AchievementFilter filter) {
        return repository.findAll(byFilter(filter));
    }

    public void createAchievement(Achievement achievement) {
        repository.save(achievement);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public String deleteByIds(List<Long> ids) {
        List<Achievement> achievements = repository.findAllById(ids);
        repository.deleteAll(achievements);
        return "Удалено " + achievements.size() + " achievement-ов";
    }

    public String deleteByFilter(AchievementFilter filter) {
        List<Achievement> achievements = repository.findAll(byFilter(filter));
        repository.deleteAll(achievements);
        return "Удалено " + achievements.size() + " achievement-ов";
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
