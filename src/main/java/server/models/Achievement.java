package server.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "achievements")
@Data
public class Achievement {
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Student.class)
    private Student student;

    @ManyToOne(targetEntity = Professor.class)
    private Professor professor;

    @ManyToOne(targetEntity = FormOfControl.class)
    private FormOfControl formOfControl;

    @ManyToOne(targetEntity = Discipline.class)
    private Discipline discipline;

    @Column(name = "mark")
    private String mark;

    @Column(name = "semester")
    private int semester;

    @Column(name = "date")
    private LocalDate date;

}
