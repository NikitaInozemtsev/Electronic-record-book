package server.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "professors")
@Data
public class Professor {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "post")
    private String post;

    @ManyToOne(targetEntity = Department.class)
    private Department department;

}
