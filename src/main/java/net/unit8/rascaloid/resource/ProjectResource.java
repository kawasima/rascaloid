package net.unit8.rascaloid.resource;

import enkan.collection.Parameters;
import kotowari.restful.Decision;
import kotowari.restful.data.Problem;
import kotowari.restful.data.RestContext;
import net.unit8.rascaloid.entity.Project;

import javax.persistence.EntityManager;

import static kotowari.restful.DecisionPoint.*;

public class ProjectResource {
    @Decision(EXISTS)
    public boolean exists(Parameters params, RestContext context, EntityManager em) {
        Project project = em.find(Project.class, params.getLong("projectId"));
        context.putValue(project);
        return project != null;
    }

    @Decision(MALFORMED)
    public Problem validation(Parameters params) {
        String projectId = params.get("projectId");
        if (projectId == null || !projectId.matches("^\\d+$")) {
            return new Problem(null, "projectId", 400, "Project ID must be number", null);
        }
        return null;
    }

    @Decision(HANDLE_OK)
    public Project handleOk(Project project) {
        return project;
    }

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
