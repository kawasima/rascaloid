package net.unit8.rascaloid.controller;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import net.unit8.rascaloid.boundary.IterationContributionPlanRequest;
import net.unit8.rascaloid.boundary.IterationCreateRequest;
import net.unit8.rascaloid.boundary.IterationStory;
import net.unit8.rascaloid.dao.IterationDao;
import net.unit8.rascaloid.dao.StoryDao;
import net.unit8.rascaloid.dao.TaskDao;
import net.unit8.rascaloid.entity.*;

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

    public Iteration show(Parameters params, UserPrincipal principal) {
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);
        return iterationDao.findById(new Identity<>(params.getLong("iterationId")), principal);

    }

    public List<IterationStory> kanban(Parameters params, UserPrincipal principal) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        List<DevelopmentTask> tasks = taskDao.findDevelopmentTasksByIterationId(new Identity<>(params.getLong("iterationId")));

        Map<Identity<Story>, List<DevelopmentTask>> storyTasks = tasks.stream()
                .collect(Collectors.groupingBy(DevelopmentTask::getStoryId));
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        return storyDao.findByIterationId(new Identity<>(params.getLong("iterationId")), principal)
                .stream()
                .map(story -> new IterationStory(
                        story.getId().getValue(),
                        story.getSubject(),
                        story.getDescription(),
                        story.getPoint(),
                        storyTasks.get(story.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void create(Parameters params, IterationCreateRequest createRequest) {
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);
        Iteration iteration = beansConverter.createFrom(createRequest, Iteration.class);
        iteration.setProjectId(params.getLong("projectId"));

        iterationDao.insert(iteration);
    }

    @Transactional
    public void addStory(Parameters params) {
        StoryDao storyDao = daoProvider.getDao(StoryDao.class);
        Story story = storyDao.findById(new Identity<>(params.getLong("storyId")));
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);

        iterationDao.addStory(new Identity<>(params.getLong("iterationId")),
                story.getId(),
                1L);
    }

    @Transactional
    public void removeStory(Parameters params) {
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);

        iterationDao.removeStory(new Identity<>(params.getLong("iterationId")),
                new Identity<>(params.getLong("storyId")));
    }

    @Transactional
    public void contributionPlan(List<IterationContributionPlanRequest> plans, Parameters params) {
        IterationDao iterationDao = daoProvider.getDao(IterationDao.class);
        Identity<Iteration> iterationId = new Identity<>(params.getLong("iterationId"));
        iterationDao.clearContributionPlan(iterationId);
        plans.stream().forEach(plan -> {
            iterationDao.addContributionPlan(iterationId, new Identity<>(plan.getUserId()), plan.getHours());
        });
    }
}
