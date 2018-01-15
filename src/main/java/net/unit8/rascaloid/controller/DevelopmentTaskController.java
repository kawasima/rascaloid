package net.unit8.rascaloid.controller;

import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import net.unit8.rascaloid.boundary.DevelopmentTaskCreateRequest;
import net.unit8.rascaloid.dao.TaskDao;
import net.unit8.rascaloid.entity.DevelopmentTask;
import net.unit8.rascaloid.entity.Task;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class DevelopmentTaskController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    @Transactional
    public void create(DevelopmentTaskCreateRequest createRequest) {
        TaskDao taskDao = daoProvider.getDao(TaskDao.class);
        DevelopmentTask task = beansConverter.createFrom(createRequest, DevelopmentTask.class);
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
