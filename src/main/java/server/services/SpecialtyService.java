package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.SpecialtyDto;
import server.exception.NotFoundResourceException;
import server.models.Discipline;
import server.models.Specialty;
import server.repositories.SpecialtyRepository;

import java.util.List;

@Service
public class SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DepartmentService departmentService;

    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }

    public Specialty findOrThrow(Long id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        Specialty.class, id
                ));
    }

    public boolean createSpecialty(SpecialtyDto specialtyDto) {
        try {
            Specialty specialty = Specialty.builder()
                    .name(specialtyDto.getName())
                    .price(specialtyDto.getPrice())
                    .department(departmentService.findOrThrow(specialtyDto.getDepartmentId()))
                    .build();
            specialtyRepository.save(specialty);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            specialtyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
