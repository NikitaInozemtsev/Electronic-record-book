package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.DisciplineDto;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.models.Discipline;
import server.repositories.DisciplineRepository;

import java.util.List;

@Service
public class DisciplineService {
    @Autowired
    DisciplineRepository disciplineRepository;
    @Autowired
    DepartmentService departmentService;

    public List<Discipline> findAll() {
        return disciplineRepository.findAll();
    }

    public Discipline findOrThrow(Long id) {
        return disciplineRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        Discipline.class, id
                ));
    }


    public boolean createDiscipline(DisciplineDto disciplineDto) {
        try {
            Discipline discipline = Discipline.builder()
                    .name(disciplineDto.getName())
                    .department(departmentService.findOrThrow(disciplineDto.getDepartmentId()))
                    .build();
            disciplineRepository.save(discipline);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            disciplineRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DisciplineDto disciplineDto) {
        try {
            Discipline disciplineFind = findOrThrow(disciplineDto.getId());
            disciplineFind.setName(disciplineDto.getName());
            disciplineFind.setDepartment(departmentService.findOrThrow(disciplineDto.getDepartmentId()));
            disciplineRepository.save(disciplineFind);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
