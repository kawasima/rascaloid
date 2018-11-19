package net.unit8.rascaloid.resource;

import enkan.component.eclipselink.EclipseLinkEntityManagerProvider;
import enkan.system.EnkanSystem;
import net.unit8.rascaloid.RascaloidSystemFactory;
import net.unit8.rascaloid.entity.Iteration;
import net.unit8.rascaloid.entity.IterationPlan;
import net.unit8.rascaloid.entity.Project;
import net.unit8.rascaloid.entity.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static enkan.util.BeanBuilder.builder;

public class IterationStoriesResourceTest {
    private EnkanSystem system;

    @BeforeEach
    public void setup() {
        system = new RascaloidSystemFactory().create();
        system.start();
    }

    @AfterEach
    public void tearDown() {
        system.stop();
    }

    @Test
    public void create() {
        EclipseLinkEntityManagerProvider entityManagerProvider = system.getComponent("jpa");
        EntityManager em = entityManagerProvider.createEntityManager();
        Project project = builder(new Project())
                .set(Project::setName, "test project")
                .set(Project::setDescription, "This is a test project.")
                .build();
        Iteration iteration = builder(new Iteration())
                .set(Iteration::setProject, project)
                .set(Iteration::setDescription, "This is the 1st iteration")
                .set(Iteration::setSubject, "1st Iteration")
                .set(Iteration::setStartOn, LocalDate.of(2018, 10, 1))
                .set(Iteration::setEndOn, LocalDate.of(2018, 10, 7))
                .build();
        iteration.setProject(project);

        Story story = builder(new Story())
                .set(Story::setSubject, "Story1")
                .set(Story::setDescription, "Story1-description")
                .set(Story::setPoint, BigDecimal.valueOf(10L))
                .set(Story::setProject, project)
                .build();

        IterationPlan iterationPlan = builder(new IterationPlan())
                .set(IterationPlan::setIteration, iteration)
                .set(IterationPlan::setStory, story)
                .set(IterationPlan::setPosition, 1L)
                .build();
        em.getTransaction().begin();
        em.persist(project);
        em.persist(iteration);
        em.persist(story);
        em.persist(iterationPlan);

        IterationStoriesResource resource = new IterationStoriesResource();
        List<Story> stories = resource.kanban(iteration, em);
        System.out.println(stories);
        em.getTransaction().rollback();
    }
}
