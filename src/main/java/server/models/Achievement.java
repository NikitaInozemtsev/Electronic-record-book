package server.models;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "achievements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Student.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Student student;

    @ManyToOne(targetEntity = Professor.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Professor professor;

    @ManyToOne(targetEntity = FormOfControl.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FormOfControl formOfControl;

    @ManyToOne(targetEntity = Discipline.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Discipline discipline;

    @Column(name = "mark")
    private String mark;

    @Column(name = "semester")
    private int semester;

    @Column(name = "date")
    private LocalDate date;

}
