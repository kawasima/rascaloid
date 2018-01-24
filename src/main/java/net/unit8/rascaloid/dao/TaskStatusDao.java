package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.TaskStatus;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.List;

public interface TaskStatusDao {
    @Select
    List<TaskStatus> findAll();

    @Insert
    int insert(TaskStatus taskStatus);

    @Update
    int update(TaskStatus taskStatus);

    @Delete
    int delete(TaskStatus taskStatus);
}
