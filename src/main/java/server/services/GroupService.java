package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.dto.GroupDto;
import server.exception.NotFoundResourceException;
import server.models.Group;
import server.repositories.GroupRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Exception> createGroup(GroupDto groupDto) {
        try {
            Group group = Group.builder()
                    .name(groupDto.getName())
                    .course(groupDto.getCourse())
                    .specialty(specialtyService.findOrThrow(groupDto.getSpecialtyId()))
                    .build();
            groupRepository.save(group);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> deleteById(Long id) {
        try {
            groupRepository.deleteById(id);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }

    public Optional<Exception> update(GroupDto groupDto) {
        try {
            Group groupFind = findOrThrow(groupDto.getId());
            groupFind.setName(groupDto.getName());
            groupFind.setCourse(groupDto.getCourse());
            groupFind.setSpecialty(specialtyService.findOrThrow(groupDto.getSpecialtyId()));
            groupRepository.save(groupFind);
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of(e);
        }
    }
}
