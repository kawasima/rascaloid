package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.User;
import org.seasar.doma.*;

@Dao
public interface UserDao {
    @Select
    User findByAccount(String account);

    @Insert
    int insert(User user);

    @Update
    int update(User user);

    @Delete
    int delete(User user);
}
