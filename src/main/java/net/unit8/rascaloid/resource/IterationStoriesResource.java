package net.unit8.rascaloid.resource;

import enkan.collection.Parameters;
import kotowari.restful.Decision;
import kotowari.restful.data.Problem;
import kotowari.restful.data.RestContext;
import net.unit8.rascaloid.entity.Iteration;
import net.unit8.rascaloid.entity.IterationPlan;
import net.unit8.rascaloid.entity.Story;
import net.unit8.rascaloid.entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static kotowari.restful.DecisionPoint.HANDLE_OK;
import static kotowari.restful.DecisionPoint.MALFORMED;
import static kotowari.restful.DecisionPoint.POST;

@Transactional
public class IterationStoriesResource {
    @Decision(value = MALFORMED, method = {"POST"})
    public Problem isMalformed(Parameters params, EntityManager em, RestContext context) {
        Iteration iteration = em.find(Iteration.class, params.getLong("iterationId"));
        context.putValue(iteration);
        return null;
    }

    @Decision(HANDLE_OK)
    public List<Story> kanban(Iteration iteration, EntityManager em) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<IterationPlan> query = builder.createQuery(IterationPlan.class);
        Root<IterationPlan> root = query.from(IterationPlan.class);
        Join<IterationPlan, Story> storyJoin = root.join("story");
        query.select(root);
        query.where(builder.equal(root.get("iteration"), iteration));
        return em.createQuery(query)
                .getResultList()
                .stream()
                .map(IterationPlan::getStory)
                .collect(Collectors.toList());
    }

    @Decision(POST)
    public Story post(Iteration iteration, Story story, EntityManager em) {
        iteration.getStories().add(story);
        em.merge(iteration);
        return story;
    }
}
