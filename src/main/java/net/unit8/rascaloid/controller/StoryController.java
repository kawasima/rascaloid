package net.unit8.rascaloid.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import net.unit8.rascaloid.boundary.StoryCreateRequest;
import net.unit8.rascaloid.dao.ProjectDao;
import net.unit8.rascaloid.dao.StoryDao;
import net.unit8.rascaloid.entity.Identity;
import net.unit8.rascaloid.entity.Project;
import net.unit8.rascaloid.entity.Story;
import org.seasar.doma.Dao;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class StoryController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    public List<Story> list(Parameters params, UserPrincipal principal) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        return storyDao.findByProjectId(new Identity<>(params.getLong("projectId")), principal);
    }

    @Transactional
    public void create(StoryCreateRequest createRequest) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        Story story = beansConverter.createFrom(createRequest, Story.class);
        storyDao.insert(story);
    }

    @Transactional
    public void update(Story story) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        storyDao.update(story);
    }

    @Transactional
    public void delete(Story story) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        storyDao.delete(story);
    }
}
