package net.unit8.rascaloid.boundary;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskStatusCreateRequest extends BoundaryBase {
    @NotBlank
    private String name;

    private Integer position;
}
