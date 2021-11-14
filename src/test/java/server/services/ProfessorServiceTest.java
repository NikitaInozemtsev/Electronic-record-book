package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.dto.ProfessorDto;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.models.Professor;
import server.repositories.DepartmentRepository;
import server.repositories.ProfessorRepository;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProfessorServiceTest extends BaseDbTestClass {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private ProfessorService professorService;

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
        List<Professor> data = List.of(
                Professor.builder()
                        .name("Иван")
                        .surname("Иванов")
                        .post("Какая-то должность")
                        .department(departmentRepository.findById(1L).get())
                        .build(),
                Professor.builder()
                        .name("Сергей")
                        .surname("Сергеев")
                        .post("Какая-то должность1")
                        .department(departmentRepository.findById(2L).get())
                        .build()
        );
        professorRepository.saveAll(data);

        Assertions.assertEquals(professorService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        Professor professor =
                professorRepository.save(Professor.builder()
                        .name("Иван")
                        .surname("Иванов")
                        .post("Какая-то должность")
                        .department(departmentRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(professorService.findOrThrow(1L), professor);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> professorService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: Professor[12]", exception.getMessage());
    }

    @Test
    public void createProfessorTest() {
        ProfessorDto dto = new ProfessorDto(1L, "Иван", "Иванов", "", "Какая-то должность", 1L);
        Professor professor =
                Professor.builder()
                        .id(1L)
                        .name("Иван")
                        .surname("Иванов")
                        .patronymic("")
                        .post("Какая-то должность")
                        .department(departmentRepository.findById(1L).get())
                        .build();
        Assertions.assertTrue(professorService.createProfessor(dto));
        Assertions.assertEquals(professorRepository.findById(1L).get(), professor);
    }

    @Test
    public void deleteByIdTest() {
        Professor professor =
                professorRepository.save(Professor.builder()
                        .name("Иван")
                        .surname("Иванов")
                        .post("Какая-то должность")
                        .department(departmentRepository.findById(1L).get())
                        .build());

        Assertions.assertFalse(professorService.deleteById(2L));
        Assertions.assertTrue(professorService.deleteById(1L));

        Assertions.assertEquals(professorRepository.findAll(), List.of());
    }

    @Test
    public void updateTest() {
        Professor professor =
                professorRepository.save(Professor.builder()
                        .name("Иван")
                        .surname("Иванов")
                        .post("Какая-то должность")
                        .department(departmentRepository.findById(1L).get())
                        .build());
        ProfessorDto professorDto = new ProfessorDto(1L, "Сергей", "Сергеев", "Сергевич", "post", 2L);
        Professor newProfessor =
                Professor.builder()
                        .id(1L)
                        .name("Сергей")
                        .surname("Сергеев")
                        .patronymic("Сергевич")
                        .post("post")
                        .department(departmentRepository.findById(2L).get())
                        .build();

        Assertions.assertTrue(professorService.update(professorDto));
        Assertions.assertEquals(professorRepository.findAll().size(), 1);
        Assertions.assertEquals(professorRepository.findById(1L).get(), newProfessor);
    }
}
