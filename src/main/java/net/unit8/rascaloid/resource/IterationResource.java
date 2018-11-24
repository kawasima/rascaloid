package net.unit8.rascaloid.resource;

import enkan.collection.Parameters;
import kotowari.restful.Decision;
import kotowari.restful.data.RestContext;
import net.unit8.rascaloid.entity.Iteration;

import javax.persistence.EntityManager;

import static kotowari.restful.DecisionPoint.EXISTS;
import static kotowari.restful.DecisionPoint.HANDLE_OK;

public class IterationResource {
    @Decision(EXISTS)
    public boolean existsProject(Parameters params, RestContext context, EntityManager em) {
        Iteration iteration = em.find(Iteration.class, params.getLong("projectId"));
        context.putValue(iteration);
        return iteration != null;
    }

    @Decision(HANDLE_OK)
    public Iteration find(Iteration iteration) {
        return iteration;
    }
}
