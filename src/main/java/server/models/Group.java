package server.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "groups")
@Data
public class Group {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "course")
    private int course;

    @ManyToOne(targetEntity = Specialty.class)
    private Specialty specialty;
}
