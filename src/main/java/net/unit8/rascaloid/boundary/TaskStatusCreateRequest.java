package net.unit8.rascaloid.boundary;

import javax.validation.constraints.NotBlank;

public class TaskStatusCreateRequest extends BoundaryBase {
    @NotBlank
    private String name;

    private Integer position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
