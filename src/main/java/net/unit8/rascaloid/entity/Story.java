package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "stories")
@Data
public class Story implements Serializable {
    @Id
    @Column(name = "story_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Identity<Story> id;

    @Column(name = "project_id")
    private Identity<Project> projectId;

    private String subject;

    private String description;

    private BigDecimal point;
}
