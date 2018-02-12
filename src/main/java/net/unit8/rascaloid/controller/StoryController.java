package net.unit8.rascaloid.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import net.unit8.rascaloid.boundary.StoryCreateRequest;
import net.unit8.rascaloid.dao.StoryDao;
import net.unit8.rascaloid.entity.Identity;
import net.unit8.rascaloid.entity.Story;

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

    public Story show(Parameters params) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        return storyDao.findById(new Identity<>(params.getLong("storyId")));
    }

    @Transactional
    public Story create(Parameters params, StoryCreateRequest createRequest) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        Story story = beansConverter.createFrom(createRequest, Story.class);
        story.setProjectId(new Identity<>(params.getLong("projectId")));
        storyDao.insert(story);

        return story;
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
