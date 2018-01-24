package net.unit8.rascaloid.controller;

import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import net.unit8.rascaloid.boundary.DevelopmentTaskCreateRequest;
import net.unit8.rascaloid.boundary.TaskStatusCreateRequest;
import net.unit8.rascaloid.dao.TaskStatusDao;
import net.unit8.rascaloid.entity.TaskStatus;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class TaskStatusController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    @Transactional
    public void create(TaskStatusCreateRequest createRequest) {
        TaskStatusDao taskStatusDao = daoProvider.getDao(TaskStatusDao.class);
        TaskStatus taskStatus = beansConverter.createFrom(createRequest, TaskStatus.class);
        taskStatusDao.insert(taskStatus);
    }
}
