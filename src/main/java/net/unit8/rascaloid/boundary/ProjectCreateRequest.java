package net.unit8.rascaloid.boundary;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProjectCreateRequest extends BoundaryBase {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
