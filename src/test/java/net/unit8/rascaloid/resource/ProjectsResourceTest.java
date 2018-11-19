package net.unit8.rascaloid.resource;

import enkan.component.eclipselink.EclipseLinkEntityManagerProvider;
import enkan.system.EnkanSystem;
import net.unit8.rascaloid.RascaloidSystemFactory;
import net.unit8.rascaloid.boundary.ProjectQuery;
import net.unit8.rascaloid.entity.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

import static enkan.util.BeanBuilder.builder;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsResourceTest {
    private EnkanSystem system;
    private EntityManager em;

    @BeforeEach
    public void setup() {
        system = new RascaloidSystemFactory().create();
        system.start();
        EclipseLinkEntityManagerProvider entityManagerProvider = system.getComponent("jpa");
        em = entityManagerProvider.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    public void tearDown() {
        em.getTransaction().rollback();
        system.stop();
    }

    @Test
    public void create() {
        Project project = builder(new Project())
                .set(Project::setName, "test project")
                .set(Project::setDescription, "This is a test project.")
                .build();
        ProjectsResource resource = new ProjectsResource();
        Project ret = resource.handleCreated(project, em);
        assertThat(ret).isEqualTo(project);

        TypedQuery<Project> query = em.createQuery("select p from Project p where p.name=:name", Project.class);
        query.setParameter("name", "test project");
        List<Project> projects = query.getResultList();
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0)).hasFieldOrPropertyWithValue("description", "This is a test project.");
    }

    @Test
    public void search() {
        ProjectsResource resource = new ProjectsResource();
        for (int i=0; i<100; i++) {
            Project project = builder(new Project())
                    .set(Project::setName, "project"+i)
                    .set(Project::setDescription, "This is a test project.")
                    .build();
            resource.handleCreated(project, em);
        }
        ProjectQuery projectQuery = builder(new ProjectQuery())
                .set(ProjectQuery::setLimit, 10)
                .set(ProjectQuery::setOffset, 5)
                .build();
        List<Project> projects = resource.list(projectQuery, em, null);
        System.out.println(projects);
    }
}
