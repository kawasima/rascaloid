package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.DevelopmentTask;
import net.unit8.rascaloid.entity.Identity;
import net.unit8.rascaloid.entity.Iteration;
import net.unit8.rascaloid.entity.Task;
import org.seasar.doma.*;

import java.util.List;

@Dao
public interface TaskDao {
    @Select
    List<DevelopmentTask> findDevelopmentTasksByIterationId(Identity<Iteration> iterationId);

    @Insert
    int insert(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);
}
