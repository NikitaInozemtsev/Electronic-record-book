package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.dto.SpecialtyDto;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.models.Specialty;
import server.repositories.DepartmentRepository;
import server.repositories.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SpecialtyServiceTest extends BaseDbTestClass {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private SpecialtyService specialtyService;

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
        List<Specialty> data = List.of(
                Specialty.builder()
                        .name("Какая-то специальность")
                        .price(228)
                        .department(departmentRepository.findById(1L).get())
                        .build(),
                Specialty.builder()
                        .name("Другая специальность")
                        .price(222301)
                        .department(departmentRepository.findById(2L).get())
                        .build()
        );
        specialtyRepository.saveAll(data);

        Assertions.assertEquals(specialtyService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        Specialty specialty =
                specialtyRepository.save(Specialty.builder()
                        .name("Какая-то специальность")
                        .price(228)
                        .department(departmentRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(specialtyService.findOrThrow(1L), specialty);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> specialtyService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: Specialty[12]", exception.getMessage());
    }

    @Test
    public void createSpecialtyTest() {
        SpecialtyDto dto = new SpecialtyDto(1L, "Какая-то специальность", 228, 1L);
        Specialty specialty =
                Specialty.builder()
                        .id(1L)
                        .name("Какая-то специальность")
                        .price(228)
                        .department(departmentRepository.findById(1L).get())
                        .build();
        Assertions.assertEquals(specialtyService.createSpecialty(dto), Optional.empty());
        Assertions.assertEquals(specialtyRepository.findById(1L).get(), specialty);
    }

    @Test
    public void deleteByIdTest() {
        Specialty specialty =
                specialtyRepository.save(Specialty.builder()
                        .name("Какая-то специальность")
                        .price(228)
                        .department(departmentRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(specialtyService.deleteById(2L).get().getMessage(),
                "No class server.models.Specialty entity with id 2 exists!");
        Assertions.assertEquals(specialtyService.deleteById(1L), Optional.empty());

        Assertions.assertEquals(specialtyRepository.findAll(), List.of());
    }

    @Test
    public void updateTest() {
        Specialty specialty =
                specialtyRepository.save(Specialty.builder()
                        .name("Какая-то специальность")
                        .price(228)
                        .department(departmentRepository.findById(1L).get())
                        .build());
        SpecialtyDto professorDto = new SpecialtyDto(1L, "Другая специальность", 222301, 2L);
        Specialty newSpecialty =
                Specialty.builder()
                        .id(1L)
                        .name("Другая специальность")
                        .price(222301)
                        .department(departmentRepository.findById(2L).get())
                        .build();

        Assertions.assertEquals(specialtyService.update(professorDto), Optional.empty());
        Assertions.assertEquals(specialtyRepository.findAll().size(), 1);
        Assertions.assertEquals(specialtyRepository.findById(1L).get(), newSpecialty);
    }
}
