package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.TaskStatus;
import org.seasar.doma.*;

import java.util.List;

@Dao
public interface TaskStatusDao {
    @Select
    List<TaskStatus> findAll();

    @Select
    TaskStatus findByName(String name);

    @Insert
    int insert(TaskStatus taskStatus);

    @Update
    int update(TaskStatus taskStatus);

    @Delete
    int delete(TaskStatus taskStatus);
}
