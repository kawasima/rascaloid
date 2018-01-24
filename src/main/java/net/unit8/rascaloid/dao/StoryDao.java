package net.unit8.rascaloid.dao;

import enkan.security.UserPrincipal;
import net.unit8.rascaloid.entity.Identity;
import net.unit8.rascaloid.entity.Project;
import net.unit8.rascaloid.entity.Story;
import org.seasar.doma.*;

import java.util.List;
import java.util.Set;

@Dao
public interface StoryDao {
    @Select
    Story findById(Identity<Story> id);

    @Select
    List<Story> findByProjectId(Identity<Project> projectId, UserPrincipal principal);

    @Select
    List<Story> findByIds(Set<Identity<Story>> ids);

    @Insert
    int insert(Story story);

    @Update
    int update(Story story);

    @Delete
    int delete(Story story);
}
