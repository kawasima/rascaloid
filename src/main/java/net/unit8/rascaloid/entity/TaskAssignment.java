package net.unit8.rascaloid.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "task_assignments")
@Data
public class TaskAssignment implements Serializable {
    @Id
    @Column(name = "task_assignment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long taskId;

    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate workOn;
}
