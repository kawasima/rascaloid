package net.unit8.rascaloid.entity;

import javax.persistence.*;

@Entity
@Table(name ="development_tasks")
public class DevelopmentTask extends Task {
    @OneToOne
    @JoinColumn(name = "story_id")
    private Story story;

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
