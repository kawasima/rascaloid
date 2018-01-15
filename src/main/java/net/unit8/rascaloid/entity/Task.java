package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tasks")
@Data
public class Task implements Serializable {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Identity<Task> id;

    private Long projectId;
    private String subject;
    private String description;
    private BigDecimal estimateHours;
}
