package net.unit8.rascaloid.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;

@Entity
@Table(name = "projects")
@Data
public class Project implements Serializable {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Identity<Project> id;
    private String name;
    private String description;
}
