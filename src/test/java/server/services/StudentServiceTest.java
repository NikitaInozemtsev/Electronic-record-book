package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.dto.StudentDto;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.models.Group;
import server.models.Specialty;
import server.models.Student;
import server.repositories.DepartmentRepository;
import server.repositories.GroupRepository;
import server.repositories.SpecialtyRepository;
import server.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StudentServiceTest extends BaseDbTestClass {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private GroupRepository groupRepository;

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
        specialtyRepository.save(Specialty.builder()
                .name("Специальность")
                .price(2356)
                .department(departmentRepository.findById(1L).get())
                .build());
        specialtyRepository.save(Specialty.builder()
                .name("Специальность 2")
                .price(190)
                .department(departmentRepository.findById(2L).get())
                .build());
        groupRepository.save(Group.builder()
                .name("Группа 1")
                .course(1)
                .specialty(specialtyRepository.findById(1L).get())
                .build());
        groupRepository.save(Group.builder()
                .name("Группа 2")
                .course(3)
                .specialty(specialtyRepository.findById(2L).get())
                .build());
    }

    @Test
    public void findAllTest() {
        List<Student> data = List.of(
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
                        .build()
        );
        studentRepository.saveAll(data);

        Assertions.assertEquals(studentService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        Student student =
                studentRepository.save(Student.builder()
                        .dateOfBirth(LocalDate.of(2000, 2, 23))
                        .name("Вася")
                        .surname("Пупкин")
                        .group(groupRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(studentService.findOrThrow(1L), student);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> studentService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: Student[12]", exception.getMessage());
    }

    @Test
    public void createStudentTest() {
        StudentDto dto = new StudentDto(1L, "Вася", "Пупкин", "", LocalDate.of(2000, 2, 23), 1L);
        Student student = Student.builder()
                .id(1L)
                .dateOfBirth(LocalDate.of(2000, 2, 23))
                .name("Вася")
                .surname("Пупкин")
                .patronymic("")
                .group(groupRepository.findById(1L).get())
                .build();
        Assertions.assertEquals(studentService.createStudent(dto), Optional.empty());
        Assertions.assertEquals(studentRepository.findById(1L).get(), student);
    }

    @Test
    public void deleteByIdTest() {
        Student student =
                studentRepository.save(Student.builder()
                        .dateOfBirth(LocalDate.of(2000, 2, 23))
                        .name("Вася")
                        .surname("Пупкин")
                        .group(groupRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(studentService.deleteById(2L).get().getMessage(),
                "No class server.models.Student entity with id 2 exists!");
        Assertions.assertEquals(studentService.deleteById(1L), Optional.empty());

        Assertions.assertEquals(studentRepository.findAll(), List.of());
    }

    @Test
    public void updateTest() {
        Student student =
                studentRepository.save(Student.builder()
                        .dateOfBirth(LocalDate.of(2000, 2, 23))
                        .name("Вася")
                        .surname("Пупкин")
                        .group(groupRepository.findById(1L).get())
                        .build());
        StudentDto studentDto = new StudentDto(1L, "Тимур", "Кабанов", "", LocalDate.of(2001, 8, 12), 2L);
        Student newStudent =
                Student.builder()
                        .id(1L)
                        .dateOfBirth(LocalDate.of(2001, 8, 12))
                        .name("Тимур")
                        .surname("Кабанов")
                        .patronymic("")
                        .group(groupRepository.findById(2L).get())
                        .build();

        Assertions.assertEquals(studentService.update(studentDto), Optional.empty());
        Assertions.assertEquals(studentRepository.findAll().size(), 1);
        Assertions.assertEquals(studentRepository.findById(1L).get(), newStudent);
    }
}
