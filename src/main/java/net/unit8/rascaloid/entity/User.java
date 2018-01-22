package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Identity<User> id;
    private String account;
}
