package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "iterations")
@Data
public class Iteration implements Serializable {
    @Id
    @Column(name = "iteration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long projectId;
    private LocalDate startOn;
    private LocalDate endOn;
}
