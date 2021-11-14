package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.ProfessorDto;
import server.exception.NotFoundResourceException;
import server.models.Professor;
import server.repositories.ProfessorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    DepartmentService departmentService;

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public Professor findOrThrow(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        Professor.class, id
                ));
    }

    public Optional<Exception> createProfessor(ProfessorDto professorDto) {
        try {
            Professor professor = Professor.builder()
                    .name(professorDto.getName())
                    .surname(professorDto.getSurname())
                    .patronymic(professorDto.getPatronymic())
                    .post(professorDto.getPost())
                    .department(departmentService.findOrThrow(professorDto.getDepartmentId()))
                    .build();
            professorRepository.save(professor);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> deleteById(Long id) {
        try {
            professorRepository.deleteById(id);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> update(ProfessorDto professorDto) {
        try {
            Professor professor = findOrThrow(professorDto.getId());
            professor.setName(professorDto.getName());
            professor.setSurname(professorDto.getSurname());
            professor.setPatronymic(professorDto.getPatronymic());
            professor.setPost(professorDto.getPost());
            professor.setDepartment(departmentService.findOrThrow(professorDto.getDepartmentId()));
            professorRepository.save(professor);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }
}
