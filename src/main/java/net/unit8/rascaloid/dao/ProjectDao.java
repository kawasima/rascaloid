package net.unit8.rascaloid.dao;

import enkan.security.UserPrincipal;
import net.unit8.rascaloid.entity.Project;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import java.util.List;

@Dao
public interface ProjectDao {
    @Select
    List<Project> findAll(UserPrincipal principal);

    @Select
    List<Project> findById(Long id, UserPrincipal principal);

    @Insert
    int insert(Project project);

    @Update
    int update(Project project);

    @Insert
    int delete(Project project);

}
