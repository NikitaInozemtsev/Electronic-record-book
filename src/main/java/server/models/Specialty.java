package server.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "specialties")
@Data
public class Specialty {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @ManyToOne(targetEntity = Department.class)
    private Department department;
}
