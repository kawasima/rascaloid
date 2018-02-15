package net.unit8.rascaloid.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    private String subject;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endOn;
}
