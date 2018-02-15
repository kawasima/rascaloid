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

    public DevelopmentTask show(Parameters params) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        return taskDao.findDevelopmentTaskById(new Identity<>(params.getLong("taskId")));
    }
    public List<DevelopmentTask> list(Parameters params) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        return taskDao.findDevelopmentTasksByStoryId(new Identity<>(params.getLong("storyId")));
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
    public void update(DevelopmentTaskCreateRequest taskRequest, Parameters params) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        DevelopmentTask task = taskDao.findDevelopmentTaskById(new Identity<>(params.getLong("taskId")));
        beansConverter.copy(taskRequest, task, BeansConverter.CopyOption.REPLACE_NON_NULL);
        taskDao.update(task);
    }

    @Transactional
    public void delete(DevelopmentTask task) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        taskDao.delete(task);
    }

}
