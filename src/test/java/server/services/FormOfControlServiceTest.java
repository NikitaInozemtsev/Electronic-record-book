package server.services;

import configuration.BaseDbTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import server.exception.NotFoundResourceException;
import server.models.FormOfControl;
import server.repositories.FormOfControlRepository;

import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FormOfControlServiceTest extends BaseDbTestClass {
    @Autowired
    private FormOfControlRepository formOfControlRepository;

    @Autowired
    private FormOfControlService formOfControlService;

    @Test
    public void findAllTest() {
        List<FormOfControl> data = List.of(
                FormOfControl.builder()
                        .name("Экзамен")
                        .build(),
                FormOfControl.builder()
                        .name("Зачет")
                        .build()
        );
        formOfControlRepository.saveAll(data);

        Assertions.assertEquals(formOfControlService.findAll(), data);
    }

    @Test
    public void findOrThrowTest() {
        FormOfControl formOfControl =
                formOfControlRepository.save(FormOfControl.builder()
                        .name("Экзамен")
                        .build());

        Assertions.assertEquals(formOfControlService.findOrThrow(1L), formOfControl);
        Throwable exception = Assertions.assertThrows(NotFoundResourceException.class,
                () -> formOfControlService.findOrThrow(12L));
        Assertions.assertEquals("Entity not found: FormOfControl[12]", exception.getMessage());
    }

}
