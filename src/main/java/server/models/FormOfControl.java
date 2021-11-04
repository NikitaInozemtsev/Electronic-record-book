package server.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "form_of_controls")
@Data
public class FormOfControl {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;
}
