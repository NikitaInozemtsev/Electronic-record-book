package server.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "disciplines")
@Data
public class Discipline {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(targetEntity = Department.class)
    private Department department;
}
