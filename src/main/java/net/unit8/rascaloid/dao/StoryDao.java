package net.unit8.rascaloid.dao;

import net.unit8.rascaloid.entity.Story;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;

@Dao
public interface StoryDao {
    @Insert
    int insert(Story story);

    @Update
    int update(Story story);

    @Delete
    int delete(Story story);
}
