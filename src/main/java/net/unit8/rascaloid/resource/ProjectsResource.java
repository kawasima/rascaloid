package net.unit8.rascaloid.resource;

import enkan.collection.Parameters;
import enkan.component.BeansConverter;
import enkan.security.UserPrincipal;
import kotowari.restful.Decision;
import kotowari.restful.component.BeansValidator;
import kotowari.restful.data.Problem;
import kotowari.restful.data.RestContext;
import net.unit8.rascaloid.boundary.ProjectQuery;
import net.unit8.rascaloid.entity.Project;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static kotowari.restful.DecisionPoint.*;

public class ProjectsResource {
    @Inject
    private BeansConverter beansConverter;

    @Inject
    private BeansValidator validator;

    @Decision(value = MALFORMED, method = {"GET"})
    public Problem isMalformed(Parameters params, RestContext context) {
        ProjectQuery searchParams = beansConverter.createFrom(params, ProjectQuery.class);
        context.putValue(searchParams);
        Set<ConstraintViolation<ProjectQuery>> violations = validator.validate(searchParams);
        return violations.isEmpty() ? null : Problem.fromViolations(violations);
    }

    @Decision(value = MALFORMED, method = {"POST"})
    public Problem isPostMalformed(Project project) {
        Set<ConstraintViolation<Project>> violations = validator.validate(project);
        return violations.isEmpty() ? null : Problem.fromViolations(violations);
    }

    @Decision(HANDLE_OK)
    public List<Project> list(ProjectQuery params, EntityManager em, UserPrincipal principal) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Project> query = builder.createQuery(Project.class);
        Root<Project> root = query.from(Project.class);
        query.select(root);

        return em.createQuery(query)
                .setFirstResult(params.getOffset())
                .setMaxResults(params.getLimit())
                .getResultList();
    }

    @Decision(POST)
    public Project handleCreated(Project project, EntityManager em) {
        em.persist(project);
        return project;
    }
}
