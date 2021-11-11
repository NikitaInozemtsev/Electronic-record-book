package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.exception.NotFoundResourceException;
import server.models.Department;
import server.repositories.DepartmentRepository;

import java.util.List;

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

    public boolean createDepartment(Department department) {
        try {
            departmentRepository.save(department);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            departmentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Department department) {
        try {
            Department departmentFind = findOrThrow(department.getId());
            departmentFind.setName(department.getName());
            departmentFind.setPhoneNumber(department.getPhoneNumber());
            departmentFind.setHeadOfTheDepartment(department.getHeadOfTheDepartment());
            departmentRepository.save(departmentFind);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
