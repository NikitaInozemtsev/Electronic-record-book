package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.dto.DisciplineDto;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.models.Discipline;
import server.repositories.DepartmentRepository;
import server.repositories.DisciplineRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DisciplineServiceTest extends BaseDbTestClass {
    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineService disciplineService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    public void setUp() {
        departmentRepository.save(Department.builder()
                .name("Какой-то department")
                .headOfTheDepartment("Some text")
                .phoneNumber("12344321")
                .build());
        departmentRepository.save(Department.builder()
                .name("Какой-то department2")
                .headOfTheDepartment("Второй текст")
                .phoneNumber("124")
                .build());
    }

    @Test
    public void findAllTest() {
        List<Discipline> data = List.of(
                Discipline.builder()
                        .name("Какая-то дисциплина")
                        .department(departmentRepository.findById(1L).get())
                        .build(),
                Discipline.builder()
                        .name("Какая-то дисциплина 2")
                        .department(departmentRepository.findById(1L).get())
                        .build()
        );
        disciplineRepository.saveAll(data);

        Assertions.assertEquals(disciplineService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        Discipline discipline =
                disciplineRepository.save(Discipline.builder()
                        .name("Какая-то дисциплина")
                        .department(departmentRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(disciplineService.findOrThrow(1L), discipline);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> disciplineService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: Discipline[12]", exception.getMessage());
    }

    @Test
    public void createDisciplineTest() {
        DisciplineDto dto = new DisciplineDto(1L, "Какая-то дисциплина", 1L);
        Discipline discipline = Discipline.builder()
                .id(1L)
                .name("Какая-то дисциплина")
                .department(departmentRepository.findById(1L).get())
                .build();
        Assertions.assertEquals(disciplineService.createDiscipline(dto), Optional.empty());
        Assertions.assertEquals(disciplineRepository.findById(1L).get(), discipline);
    }

    @Test
    public void deleteByIdTest() {
        Discipline discipline =
                disciplineRepository.save(Discipline.builder()
                        .name("Какая-то дисциплина")
                        .department(departmentRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(disciplineService.deleteById(2L).get().getMessage(),
                "No class server.models.Discipline entity with id 2 exists!");
        Assertions.assertEquals(disciplineService.deleteById(1L), Optional.empty());

        Assertions.assertEquals(disciplineRepository.findAll(), List.of());
    }

    @Test
    public void updateTest() {
        Discipline discipline =
                disciplineRepository.save(Discipline.builder()
                        .name("Какая-то дисциплина")
                        .department(departmentRepository.findById(1L).get())
                        .build());
        DisciplineDto disciplineDto = new DisciplineDto(1L, "другой текст", 2L);
        Discipline newDiscipline =
                Discipline.builder()
                        .id(1L)
                        .name("другой текст")
                        .department(departmentRepository.findById(2L).get())
                        .build();

        Assertions.assertEquals(disciplineService.update(disciplineDto), Optional.empty());
        Assertions.assertEquals(disciplineRepository.findAll().size(), 1);
        Assertions.assertEquals(disciplineRepository.findById(1L).get(), newDiscipline);
    }

}
