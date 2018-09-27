package net.unit8.rascaloid.dao;

import enkan.security.UserPrincipal;
import net.unit8.rascaloid.entity.*;
import org.seasar.doma.*;

import java.math.BigDecimal;
import java.util.List;

@Dao
public interface IterationDao {
    @Select
    Iteration findById(Identity<Iteration> id, UserPrincipal principal);

    @Select
    List<Iteration> findByProjectId(Identity<Project> projectId, UserPrincipal principal);

    @Insert
    int insert(Iteration iteration);

    @Update
    int update(Iteration iteration);

    @Delete
    int delete(Iteration iteration);

    @Insert(sqlFile = true)
    int addStory(Identity<Iteration> id, Identity<Story> storyId, Long position);

    @Delete(sqlFile = true)
    int removeStory(Identity<Iteration> id, Identity<Story> storyId);

    @Insert(sqlFile = true)
    int addContributionPlan(Identity<Iteration> id, Identity<User> userId, BigDecimal hours);

    @Delete(sqlFile = true)
    int clearContributionPlan(Identity<Iteration> id);
}
