package net.unit8.rascaloid.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;
import org.seasar.doma.Domain;

@Domain(valueType = Long.class)
@Value
public class Identity<T> {
    @JsonValue
    private final Long value;
}
