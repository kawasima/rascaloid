package net.unit8.rascaloid.resource;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import kotowari.restful.Decision;
import kotowari.restful.component.BeansValidator;
import kotowari.restful.data.RestContext;
import net.unit8.rascaloid.entity.Iteration;
import net.unit8.rascaloid.entity.Project;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;

import static kotowari.restful.DecisionPoint.EXISTS;
import static kotowari.restful.DecisionPoint.HANDLE_OK;

public class IterationsResource {
    @Inject
    private BeansConverter beansConverter;

    @Inject
    private BeansValidator validator;

    @Decision(EXISTS)
    public boolean existsProject(Parameters params, RestContext context, EntityManager em) {
        Project project = em.find(Project.class, params.getLong("projectId"));
        context.putValue(project);
        return project != null;
    }

    @Decision(HANDLE_OK)
    public List<Iteration> list(Project project, EntityManager em) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Iteration> query = builder.createQuery(Iteration.class);
        return null;
    }
}
