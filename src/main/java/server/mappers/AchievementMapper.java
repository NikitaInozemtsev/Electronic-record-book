package server.mappers;

import server.dto.AchievementResponseDto;
import server.models.Achievement;

public class AchievementMapper {

    public static AchievementResponseDto mapToDto(Achievement achievement) {
        return AchievementResponseDto.builder()
                .id(achievement.getId())
                .fullNameStudent(achievement.getStudent().getFullName())
                .fullNameProfessor(achievement.getProfessor().getFullName())
                .formOfControlName(achievement.getFormOfControl().getName())
                .disciplineName(achievement.getDiscipline().getName())
                .mark(achievement.getMark())
                .semester(achievement.getSemester())
                .date(achievement.getDate())
                .build();
    }

}
