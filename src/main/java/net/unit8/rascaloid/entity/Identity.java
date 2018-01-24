package net.unit8.rascaloid.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.seasar.doma.Domain;

@Domain(valueType = Long.class)
public class Identity<T> {
    @JsonCreator
    public Identity(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @JsonValue
    private final Long value;
}
