package server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDto {

    private Long id;
    private Long studentId;
    private Long professorId;
    private Long formOfControlId;
    private Long disciplineId;
    private String mark;
    private Integer semester;
    private LocalDate date;
}
