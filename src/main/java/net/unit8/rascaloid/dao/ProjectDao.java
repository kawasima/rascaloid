package net.unit8.rascaloid.dao;

import enkan.security.UserPrincipal;
import net.unit8.rascaloid.entity.Identity;
import net.unit8.rascaloid.entity.Project;
import net.unit8.rascaloid.entity.User;
import org.seasar.doma.*;

import java.util.List;

@Dao
public interface ProjectDao {
    @Select
    List<Project> findAll(UserPrincipal principal);

    @Select
    List<Project> findById(Identity<Project> id, UserPrincipal principal);

    @Insert
    int insert(Project project);

    @Update
    int update(Project project);

    @Delete
    int delete(Project project);

    @Insert(sqlFile = true)
    int addUser(Identity<Project> id, Identity<User> userId);
}
