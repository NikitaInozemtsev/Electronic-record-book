package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.SpecialtyDto;
import server.dto.StudentDto;
import server.exception.NotFoundResourceException;
import server.models.Specialty;
import server.models.Student;
import server.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupService groupService;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        Student.class, id
                ));
    }

    public boolean createStudent(StudentDto studentDto) {
        try {
            Student student = Student.builder()
                    .name(studentDto.getName())
                    .surname(studentDto.getSurname())
                    .patronymic(studentDto.getPatronymic())
                    .dateOfBirth(studentDto.getDateOfBirth())
                    .group(groupService.findOrThrow(studentDto.getGroupId()))
                    .build();
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(StudentDto studentDto) {
        try {
            Student student = findOrThrow(studentDto.getId());
            student.setName(studentDto.getName());
            student.setSurname(studentDto.getSurname());
            student.setPatronymic(studentDto.getPatronymic());
            student.setDateOfBirth(studentDto.getDateOfBirth());
            student.setGroup(groupService.findOrThrow(studentDto.getGroupId()));
            studentRepository.save(student);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
