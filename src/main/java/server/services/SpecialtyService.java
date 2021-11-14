package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.SpecialtyDto;
import server.exception.NotFoundResourceException;
import server.models.Specialty;
import server.repositories.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Exception> createSpecialty(SpecialtyDto specialtyDto) {
        try {
            Specialty specialty = Specialty.builder()
                    .name(specialtyDto.getName())
                    .price(specialtyDto.getPrice())
                    .department(departmentService.findOrThrow(specialtyDto.getDepartmentId()))
                    .build();
            specialtyRepository.save(specialty);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> deleteById(Long id) {
        try {
            specialtyRepository.deleteById(id);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> update(SpecialtyDto specialtyDto) {
        try {
            Specialty specialty = findOrThrow(specialtyDto.getId());
            specialty.setName(specialtyDto.getName());
            specialty.setPrice(specialtyDto.getPrice());
            specialty.setDepartment(departmentService.findOrThrow(specialtyDto.getDepartmentId()));
            specialtyRepository.save(specialty);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }
}
