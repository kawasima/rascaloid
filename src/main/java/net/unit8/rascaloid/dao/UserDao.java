package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.User;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;

@Dao
public interface UserDao {
    @Insert
    int insert(User user);

    @Update
    int update(User user);

    @Delete
    int delete(User user);
}
