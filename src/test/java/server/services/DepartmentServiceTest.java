package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DepartmentServiceTest extends BaseDbTestClass {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void findAllTest() {
        List<Department> data = List.of(
                Department.builder()
                        .name("Какой-то department")
                        .headOfTheDepartment("Some text")
                        .phoneNumber("12344321")
                        .build(),
                Department.builder()
                        .name("Какой-то department2")
                        .headOfTheDepartment("Some text2")
                        .phoneNumber("12342")
                        .build()
        );
        departmentRepository.saveAll(data);

        Assertions.assertEquals(departmentService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        Department department =
                departmentRepository.save(Department.builder()
                        .name("Какой-то department")
                        .headOfTheDepartment("Some text")
                        .phoneNumber("12344321")
                        .build());

        Assertions.assertEquals(departmentService.findOrThrow(1L), department);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> departmentService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: Department[12]", exception.getMessage());
    }

    @Test
    public void createDepartmentTest() {
        Department department = Department.builder()
                .name("Какой-то department")
                .headOfTheDepartment("Some text")
                .phoneNumber("12344321")
                .build();
        Assertions.assertEquals(departmentService.createDepartment(department), Optional.empty());
        Assertions.assertEquals(departmentRepository.findById(1L).get(), department);
    }

    @Test
    public void deleteByIdTest() {
        Department department =
                departmentRepository.save(Department.builder()
                        .name("Какой-то department")
                        .headOfTheDepartment("Some text")
                        .phoneNumber("12344321")
                        .build());

        Assertions.assertEquals(departmentService.deleteById(2L).get().getMessage(),
                "No class server.models.Department entity with id 2 exists!");
        Assertions.assertEquals(departmentService.deleteById(1L), Optional.empty());

        Assertions.assertEquals(departmentRepository.findAll(), List.of());
    }

    @Test
    public void updateTest() {
        Department department =
                departmentRepository.save(Department.builder()
                        .name("Какой-то department")
                        .headOfTheDepartment("Some text")
                        .phoneNumber("12344321")
                        .build());
        Department newDepartment = Department.builder()
                .id(department.getId())
                .name("Другой department")
                .headOfTheDepartment("Другой текст")
                .phoneNumber("092396")
                .build();

        Assertions.assertEquals(departmentService.update(newDepartment), Optional.empty());
        Assertions.assertEquals(departmentRepository.findAll().size(), 1);
        Assertions.assertEquals(departmentRepository.findById(1L).get(), newDepartment);
    }

}
