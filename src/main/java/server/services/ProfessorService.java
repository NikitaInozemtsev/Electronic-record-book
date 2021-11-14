package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.ProfessorDto;
import server.exception.NotFoundResourceException;
import server.models.Professor;
import server.repositories.ProfessorRepository;

import java.util.List;

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

    public boolean createProfessor(ProfessorDto professorDto) {
        try {
            Professor professor = Professor.builder()
                    .name(professorDto.getName())
                    .surname(professorDto.getSurname())
                    .patronymic(professorDto.getPatronymic())
                    .post(professorDto.getPost())
                    .department(departmentService.findOrThrow(professorDto.getDepartmentId()))
                    .build();
            professorRepository.save(professor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            professorRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(ProfessorDto professorDto) {
        try {
            Professor professor = findOrThrow(professorDto.getId());
            professor.setName(professorDto.getName());
            professor.setSurname(professorDto.getSurname());
            professor.setPatronymic(professorDto.getPatronymic());
            professor.setPost(professorDto.getPost());
            professor.setDepartment(departmentService.findOrThrow(professorDto.getDepartmentId()));
            professorRepository.save(professor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
