package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.*;
import org.seasar.doma.*;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.builder.InsertBuilder;

import java.util.List;

@Dao
public interface TaskDao {
    @Select
    List<DevelopmentTask> findDevelopmentTasksByStoryId(Identity<Story> storyId);

    @Select
    List<DevelopmentTask> findDevelopmentTasksByIterationId(Identity<Iteration> iterationId);

    default int insert(DevelopmentTask devTask) {
        insert((Task) devTask);
        Config config = Config.get(this);
        return InsertBuilder.newInstance(config)
                .sql("INSERT INTO development_tasks(task_id, story_id) values(")
                .param(Identity.class, devTask.getId())
                .sql(",")
                .param(Identity.class, devTask.getStoryId())
                .sql(")")
                .execute();
    }

    @Insert
    int insert(Task task);

    @Update
    int update(Task task);

    @Delete
    int delete(Task task);
}
