package server.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
public class AchievementResponseDto {

    private Long id;
    private String fullNameStudent;
    private String fullNameProfessor;
    private String formOfControlName;
    private String disciplineName;
    private String mark;
    private int semester;
    private LocalDate date;

}
