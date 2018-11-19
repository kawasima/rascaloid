package net.unit8.rascaloid.boundary;

import javax.validation.constraints.NotBlank;

public class IterationCreateRequest extends BoundaryBase {
    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @NotBlank
    private String startOn;

    @NotBlank
    private String endOn;

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

    public String getStartOn() {
        return startOn;
    }

    public void setStartOn(String startOn) {
        this.startOn = startOn;
    }

    public String getEndOn() {
        return endOn;
    }

    public void setEndOn(String endOn) {
        this.endOn = endOn;
    }
}
