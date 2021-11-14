package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findOrThrow(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        Department.class, id
                ));
    }

    public Optional<Exception> createDepartment(Department department) {
        try {
            departmentRepository.save(department);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> deleteById(Long id) {
        try {
            departmentRepository.deleteById(id);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> update(Department department) {
        try {
            Department departmentFind = findOrThrow(department.getId());
            departmentFind.setName(department.getName());
            departmentFind.setPhoneNumber(department.getPhoneNumber());
            departmentFind.setHeadOfTheDepartment(department.getHeadOfTheDepartment());
            departmentRepository.save(departmentFind);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

}
