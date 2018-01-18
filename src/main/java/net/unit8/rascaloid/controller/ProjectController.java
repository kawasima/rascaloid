package net.unit8.rascaloid.controller;

import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.security.UserPrincipal;
import enkan.security.bouncr.UserPermissionPrincipal;
import net.unit8.rascaloid.boundary.ProjectCreateRequest;
import net.unit8.rascaloid.dao.ProjectDao;
import net.unit8.rascaloid.dao.UserDao;
import net.unit8.rascaloid.entity.Project;
import net.unit8.rascaloid.entity.User;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class ProjectController {
    @Inject
    private DomaProvider daoProvider;

    @Inject
    private BeansConverter beansConverter;

    @RolesAllowed("SHOW_PROJECT")
    public List<Project> list(UserPrincipal principal) {
        ProjectDao projectDao = daoProvider.getDao(ProjectDao.class);
        return projectDao.findAll(principal);
    }

    @Transactional
    public void create(ProjectCreateRequest createRequest, UserPermissionPrincipal principal) {
        ProjectDao projectDao = daoProvider.getDao(ProjectDao.class);
        Project project = beansConverter.createFrom(createRequest, Project.class);
        projectDao.insert(project);

        UserDao userDao = daoProvider.getDao(UserDao.class);
        User user = userDao.findByAccount(principal.getName());
        projectDao.addUser(project.getId(), user.getId());
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
