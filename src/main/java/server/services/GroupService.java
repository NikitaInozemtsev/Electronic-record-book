package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.DisciplineDto;
import server.dto.GroupDto;
import server.exception.NotFoundResourceException;
import server.models.Discipline;
import server.models.Group;
import server.repositories.GroupRepository;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SpecialtyService specialtyService;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findOrThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException(
                        Group.class, id
                ));
    }

    public boolean createGroup(GroupDto groupDto) {
        try {
            Group group = Group.builder()
                    .name(groupDto.getName())
                    .course(groupDto.getCourse())
                    .specialty(specialtyService.findOrThrow(groupDto.getSpecialtyId()))
                    .build();
            groupRepository.save(group);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(Long id) {
        try {
            groupRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(GroupDto groupDto) {
        try {
            Group groupFind = findOrThrow(groupDto.getId());
            groupFind.setName(groupDto.getName());
            groupFind.setCourse(groupDto.getCourse());
            groupFind.setSpecialty(specialtyService.findOrThrow(groupDto.getSpecialtyId()));
            groupRepository.save(groupFind);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
