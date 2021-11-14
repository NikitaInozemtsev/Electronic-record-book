package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.dto.GroupDto;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.models.Group;
import server.models.Specialty;
import server.repositories.DepartmentRepository;
import server.repositories.GroupRepository;
import server.repositories.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GroupServiceTest extends BaseDbTestClass {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private SpecialtyRepository specialtyRepository;

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
    }

    @Test
    public void findAllTest() {
        List<Group> data = List.of(
                Group.builder()
                        .name("Группа 1")
                        .course(1)
                        .specialty(specialtyRepository.findById(1L).get())
                        .build(),
                Group.builder()
                        .name("Группа 2")
                        .course(3)
                        .specialty(specialtyRepository.findById(2L).get())
                        .build()
        );
        groupRepository.saveAll(data);

        Assertions.assertEquals(groupService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        Group group =
                groupRepository.save(Group.builder()
                        .name("Группа 1")
                        .course(1)
                        .specialty(specialtyRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(groupService.findOrThrow(1L), group);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> groupService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: Group[12]", exception.getMessage());
    }

    @Test
    public void createGroupTest() {
        GroupDto dto = new GroupDto(1L, "Группа 1", 1, 1L);
        Group group = Group.builder()
                .id(1L)
                .name("Группа 1")
                .course(1)
                .specialty(specialtyRepository.findById(1L).get())
                .build();
        Assertions.assertEquals(groupService.createGroup(dto), Optional.empty());
        Assertions.assertEquals(groupRepository.findById(1L).get(), group);
    }

    @Test
    public void deleteByIdTest() {
        Group group =
                groupRepository.save(Group.builder()
                        .name("Группа 1")
                        .course(1)
                        .specialty(specialtyRepository.findById(1L).get())
                        .build());

        Assertions.assertEquals(groupService.deleteById(2L).get().getMessage(),
                "No class server.models.Group entity with id 2 exists!");
        Assertions.assertEquals(groupService.deleteById(1L), Optional.empty());

        Assertions.assertEquals(groupRepository.findAll(), List.of());
    }

    @Test
    public void updateTest() {
        Group group =
                groupRepository.save(Group.builder()
                        .name("Группа 1")
                        .course(1)
                        .specialty(specialtyRepository.findById(1L).get())
                        .build());
        GroupDto groupDto = new GroupDto(1L, "2 группа", 2, 2L);
        Group newGroup =
                Group.builder()
                        .id(1L)
                        .name("2 группа")
                        .course(2)
                        .specialty(specialtyRepository.findById(2L).get())
                        .build();

        Assertions.assertEquals(groupService.update(groupDto), Optional.empty());
        Assertions.assertEquals(groupRepository.findAll().size(), 1);
        Assertions.assertEquals(groupRepository.findById(1L).get(), newGroup);
    }
}
