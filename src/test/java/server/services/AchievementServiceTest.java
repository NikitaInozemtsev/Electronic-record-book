package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import server.dto.AchievementDto;
import server.filters.AchievementFilter;
import server.models.*;
import server.repositories.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AchievementServiceTest extends BaseDbTestClass {
    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);

    @Autowired
    private AchievementService service;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private FormOfControlRepository formOfControlRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @BeforeEach
    public void setUp() {
        formOfControlRepository.saveAll(List.of(
                FormOfControl.builder()
                        .name("Экзамен")
                        .build(),
                FormOfControl.builder()
                        .name("Зачет")
                        .build()
        ));

        departmentRepository.save(Department.builder()
                .name("Какая-то кафедра")
                .phoneNumber("1234567890")
                .headOfTheDepartment("Empty")
                .build());

        disciplineRepository.saveAll(List.of(
                Discipline.builder()
                        .name("Какой-то предмет")
                        .department(departmentRepository.findById(1L).get())
                        .build(),
                Discipline.builder()
                        .name("Какой-то предмет 2")
                        .department(departmentRepository.findById(1L).get())
                        .build()
        ));

        specialtyRepository.save(
                Specialty.builder()
                        .name("Какая-то специальность")
                        .price(2390)
                        .department(departmentRepository.findById(1L).get())
                        .build());

        groupRepository.saveAll(List.of(
                Group.builder()
                        .name("группа 1")
                        .course(3)
                        .specialty(specialtyRepository.findById(1L).get())
                        .build(),
                Group.builder()
                        .name("группа 2")
                        .course(1)
                        .specialty(specialtyRepository.findById(1L).get())
                        .build()
        ));

        studentRepository.saveAll(List.of(
                Student.builder()
                        .dateOfBirth(LocalDate.of(2000, 2, 23))
                        .name("Вася")
                        .surname("Пупкин")
                        .group(groupRepository.findById(1L).get())
                        .build(),
                Student.builder()
                        .dateOfBirth(LocalDate.of(2001, 8, 12))
                        .name("Тимур")
                        .surname("Кабанов")
                        .group(groupRepository.findById(1L).get())
                        .build(),
                Student.builder()
                        .dateOfBirth(LocalDate.of(2003, 12, 9))
                        .name("Витольд")
                        .surname("Лазарев")
                        .group(groupRepository.findById(2L).get())
                        .build()
        ));

        professorRepository.saveAll(List.of(
                Professor.builder()
                        .name("Иван")
                        .surname("Иванов")
                        .post("Какая-то должность")
                        .department(departmentRepository.findById(1L).get())
                        .build(),
                Professor.builder()
                        .name("Андрей")
                        .surname("Воробьёв")
                        .post("Ассистент")
                        .department(departmentRepository.findById(1L).get())
                        .build()
        ));

    }

    @Test
    public void findAllTest() {

        List<Achievement> data = List.of(
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 16))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(1L).get())
                        .semester(3)
                        .mark("Хорошо")
                        .professor(professorRepository.findById(1L).get())
                        .student(studentRepository.findById(1L).get())
                        .build(),
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 18))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(2L).get())
                        .semester(3)
                        .mark("Отлично")
                        .professor(professorRepository.findById(2L).get())
                        .student(studentRepository.findById(2L).get())
                        .build(),
                Achievement.builder()
                        .date(LocalDate.of(2020, 12, 26))
                        .formOfControl(formOfControlRepository.findByName("Зачет"))
                        .discipline(disciplineRepository.findById(1L).get())
                        .semester(1)
                        .mark("Зачтено")
                        .professor(professorRepository.findById(2L).get())
                        .student(studentRepository.findById(3L).get())
                        .build()
        );

        achievementRepository.saveAll(data);

        AchievementFilter filter = new AchievementFilter();

        Assertions.assertEquals(service.findAll(new AchievementFilter(), DEFAULT_PAGEABLE).getContent().get(0).getId(),
                data.get(0).getId());

        filter.setDateFrom(LocalDate.of(2021, 1, 17));
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(1, 2));

        filter.setDateFrom(LocalDate.of(2021, 1, 15));
        filter.setDateTo(LocalDate.of(2021, 1, 20));
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(0, 2));

        filter = new AchievementFilter();
        filter.setFormOfControlId(1L);
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(0, 2));

        filter = new AchievementFilter();
        filter.setStudentId(3L);
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(2, 3));

        filter = new AchievementFilter();
        filter.setGroupId(1L);
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(0, 2));

        filter = new AchievementFilter();
        filter.setProfessorId(2L);
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(1, 3));

        filter = new AchievementFilter();
        filter.setSemester(3);
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(0, 2));

        filter = new AchievementFilter();
        filter.setSemester(3);
        filter.setStudentId(1L);
        filter.setFormOfControlId(1L);
        filter.setProfessorId(1L);
        filter.setGroupId(1L);
        filter.setDateFrom(LocalDate.of(2021, 1, 16));
        Assertions.assertEquals(service.findAll(filter, DEFAULT_PAGEABLE).getContent(), data.subList(0, 1));
    }

    @Test
    public void deleteByIdTest() {
        Achievement achievement =
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 16))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(1L).get())
                        .semester(3)
                        .mark("Хорошо")
                        .professor(professorRepository.findById(1L).get())
                        .student(studentRepository.findById(1L).get())
                        .build();

        achievementRepository.save(achievement);

        Assertions.assertEquals(achievementRepository.findById(1L).get(), achievement);

        service.deleteById(1L);

        Assertions.assertEquals(achievementRepository.findById(1L), Optional.empty());
    }

    @Test
    public void deleteByIdsTest() {
        List<Achievement> data = List.of(
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 16))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(1L).get())
                        .semester(3)
                        .mark("Хорошо")
                        .professor(professorRepository.findById(1L).get())
                        .student(studentRepository.findById(1L).get())
                        .build(),
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 18))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(2L).get())
                        .semester(3)
                        .mark("Отлично")
                        .professor(professorRepository.findById(2L).get())
                        .student(studentRepository.findById(2L).get())
                        .build()
        );

        achievementRepository.saveAll(data);

        Assertions.assertEquals(achievementRepository.findAll(), data);

        service.deleteByIds(List.of(data.get(0).getId(), data.get(1).getId()));

        Assertions.assertTrue(achievementRepository.findAll().isEmpty());
    }

    @Test
    public void deleteByFilterTest() {
        List<Achievement> data = List.of(
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 16))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(1L).get())
                        .semester(3)
                        .mark("Хорошо")
                        .professor(professorRepository.findById(1L).get())
                        .student(studentRepository.findById(1L).get())
                        .build(),
                Achievement.builder()
                        .date(LocalDate.of(2021, 1, 18))
                        .formOfControl(formOfControlRepository.findByName("Экзамен"))
                        .discipline(disciplineRepository.findById(2L).get())
                        .semester(3)
                        .mark("Отлично")
                        .professor(professorRepository.findById(2L).get())
                        .student(studentRepository.findById(2L).get())
                        .build(),
                Achievement.builder()
                        .date(LocalDate.of(2020, 12, 26))
                        .formOfControl(formOfControlRepository.findByName("Зачет"))
                        .discipline(disciplineRepository.findById(1L).get())
                        .semester(1)
                        .mark("Зачтено")
                        .professor(professorRepository.findById(2L).get())
                        .student(studentRepository.findById(3L).get())
                        .build()
        );

        achievementRepository.saveAll(data);

        Assertions.assertEquals(achievementRepository.findAll(), data);
        AchievementFilter filter = new AchievementFilter();
        filter.setSemester(3);
        service.deleteByFilter(filter);

        Assertions.assertEquals(achievementRepository.findAll().size(), 1);
        Assertions.assertEquals(achievementRepository.findAll(), data.subList(2, 3));
    }

    @Test
    public void createAchievementTest() {
        AchievementDto dto = new AchievementDto(1L,
                1L,
                1L,
                1L,
                1L,
                "Хорошо",
                3,
                LocalDate.of(2021, 1, 16));
        Achievement achievement = Achievement.builder()
                .id(1L)
                .date(LocalDate.of(2021, 1, 16))
                .formOfControl(formOfControlRepository.findByName("Экзамен"))
                .discipline(disciplineRepository.findById(1L).get())
                .semester(3)
                .mark("Хорошо")
                .professor(professorRepository.findById(1L).get())
                .student(studentRepository.findById(1L).get())
                .build();
        service.createAchievement(dto);

        Assertions.assertEquals(achievementRepository.findAll(), List.of(achievement));
    }

}