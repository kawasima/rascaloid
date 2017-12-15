package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.Task;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;

@Dao
public interface TaskDao {
    @Insert
    int insert(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);
}
