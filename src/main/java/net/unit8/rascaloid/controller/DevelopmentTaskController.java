package net.unit8.rascaloid.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import net.unit8.rascaloid.boundary.DevelopmentTaskCreateRequest;
import net.unit8.rascaloid.dao.StoryDao;
import net.unit8.rascaloid.dao.TaskDao;
import net.unit8.rascaloid.dao.TaskStatusDao;
import net.unit8.rascaloid.entity.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DevelopmentTaskController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    public List<DevelopmentTask> list(Parameters params) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        List<DevelopmentTask> tasks = taskDao.findDevelopmentTasksByStoryId(new Identity<>(params.getLong("storyId")));
        return tasks;
    }

    @Transactional
    public void create(Parameters params, DevelopmentTaskCreateRequest createRequest) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        Story story = storyDao.findById(new Identity<>(params.getLong("storyId")));

        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        DevelopmentTask task = beansConverter.createFrom(createRequest, DevelopmentTask.class);
        task.setStoryId(story.getId());
        task.setProjectId(story.getProjectId());

        TaskStatusDao taskStatusDao = daoProvider.getDao(TaskStatusDao.class);
        taskDao.insert(task);
    }

    @Transactional
    public void update(DevelopmentTask task) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        taskDao.update(task);
    }

    @Transactional
    public void delete(DevelopmentTask task) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        taskDao.delete(task);
    }

}
