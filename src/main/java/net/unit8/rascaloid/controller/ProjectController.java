package net.unit8.rascaloid.controller;

import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import net.unit8.rascaloid.boundary.ProjectCreateRequest;
import net.unit8.rascaloid.dao.ProjectDao;
import net.unit8.rascaloid.entity.Project;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class ProjectController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    public List<Project> list(UserPrincipal principal) {
        ProjectDao projectDao = daoProvider.getDao(ProjectDao.class);
        return projectDao.findAll(principal);
    }

    @Transactional
    public void create(ProjectCreateRequest createRequest) {
        ProjectDao projectDao = daoProvider.getDao(ProjectDao.class);
        Project project = beansConverter.createFrom(createRequest, Project.class);
        projectDao.insert(project);
    }

    @Transactional
    public void update(Project project) {
        ProjectDao projectDao = daoProvider.getDao(ProjectDao.class);
        projectDao.update(project);
    }

    @Transactional
    public void delete(Project project) {
        ProjectDao projectDao = daoProvider.getDao(ProjectDao.class);
        projectDao.delete(project);
    }
}
