package net.unit8.rascaloid.resource;

import kotowari.restful.Decision;
import net.unit8.rascaloid.entity.Project;

import javax.persistence.EntityManager;

import static kotowari.restful.DecisionPoint.DELETE;
import static kotowari.restful.DecisionPoint.PUT;

public class ProjectResource {
    @Decision(PUT)
    public Project handleUpdated(Project project, EntityManager em) {
        em.merge(project);
        return project;
    }

    @Decision(DELETE)
    public Project handleDeleted(Project project, EntityManager em) {
        em.remove(project);
        return project;
    }

}
