package net.unit8.rascaloid.boundary;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectCreateRequest extends BoundaryBase {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
