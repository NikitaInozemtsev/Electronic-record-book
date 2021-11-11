package server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String post;
    private Long departmentId;
}
