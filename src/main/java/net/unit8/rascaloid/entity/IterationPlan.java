package net.unit8.rascaloid.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "iteration_plans")
public class IterationPlan implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "iteration_id")
    private Iteration iteration;

    @Id
    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;
    private Long position;

    public Iteration getIteration() {
        return iteration;
    }

    public void setIteration(Iteration iteration) {
        this.iteration = iteration;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
