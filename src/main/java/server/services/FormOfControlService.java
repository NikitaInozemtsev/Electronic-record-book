package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.exception.NotFoundResourceException;
import server.models.Discipline;
import server.models.FormOfControl;
import server.repositories.FormOfControlRepository;

import java.util.List;

@Service
public class FormOfControlService {
    @Autowired
    private FormOfControlRepository formOfControlRepository;

    public List<FormOfControl> findAll() {
        return formOfControlRepository.findAll();
    }

    public FormOfControl findOrThrow(Long id) {
        return formOfControlRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        FormOfControl.class, id
                ));
    }
}
