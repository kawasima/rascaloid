package net.unit8.rascaloid.entity;

import javax.persistence.*;

@Entity
@Table(name ="development_tasks")
public class DevelopmentTask extends Task {
    @OneToOne
    @JoinColumn(name = "story_id")
    private Long story;

    public Long getStory() {
        return story;
    }

    public void setStory(Long storyId) {
        this.story = story;
    }
}
