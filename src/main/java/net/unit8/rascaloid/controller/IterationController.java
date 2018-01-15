package net.unit8.rascaloid.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import net.unit8.rascaloid.boundary.IterationCreateRequest;
import net.unit8.rascaloid.boundary.IterationStory;
import net.unit8.rascaloid.boundary.ProjectCreateRequest;
import net.unit8.rascaloid.dao.IterationDao;
import net.unit8.rascaloid.dao.ProjectDao;
import net.unit8.rascaloid.dao.StoryDao;
import net.unit8.rascaloid.dao.TaskDao;
import net.unit8.rascaloid.entity.*;
import org.seasar.doma.Dao;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IterationController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    public List<Iteration> list(Parameters params, UserPrincipal principal) {
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);
        return iterationDao.findByProjectId(new Identity<>(params.getLong("projectId")), principal);
    }

    public List<IterationStory> kanban(Parameters params) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        List<DevelopmentTask> tasks = taskDao.findDevelopmentTasksByIterationId(new Identity<>(params.getLong("iterationId")));

        Map<Identity<Story>, List<DevelopmentTask>> storyTasks = tasks.stream()
                .collect(Collectors.groupingBy(DevelopmentTask::getStoryId));
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        return storyDao.findByIds(storyTasks.keySet())
                .stream()
                .map(story -> new IterationStory(
                        story.getSubject(),
                        story.getDescription(),
                        story.getPoint(),
                        storyTasks.get(story.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void create(IterationCreateRequest createRequest) {
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);
        Iteration iteration = beansConverter.createFrom(createRequest, Iteration.class);
        iterationDao.insert(iteration);
    }

    @Transactional
    public void addStory(Parameters params) {

    }
}
