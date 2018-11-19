package net.unit8.rascaloid.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "iterations")
public class Iteration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String subject;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "start_on")
    private LocalDate startOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "end_on")
    private LocalDate endOn;

    @ManyToMany
    @JoinTable(name="iteration_plans",
            joinColumns = @JoinColumn(name = "iterationId"),
            inverseJoinColumns = @JoinColumn(name = "storyId"))
    private List<Story> stories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartOn() {
        return startOn;
    }

    public void setStartOn(LocalDate startOn) {
        this.startOn = startOn;
    }

    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }
}
