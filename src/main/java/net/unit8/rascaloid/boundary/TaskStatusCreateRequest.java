package net.unit8.rascaloid.boundary;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskStatusCreateRequest extends BoundaryBase {
    @NotBlank
    private String name;

    private Integer position;
}
