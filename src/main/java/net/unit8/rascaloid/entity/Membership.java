package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import java.io.Serializable;

@Entity
@Table(name = "memberships")
@Data
public class Membership implements Serializable {
    @Id
    private Long projectId;

    @Id
    private Long userId;

}
