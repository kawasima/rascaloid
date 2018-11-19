package net.unit8.rascaloid;

import enkan.Env;
import enkan.collection.OptionMap;
import enkan.component.ApplicationComponent;
import enkan.component.eclipselink.EclipseLinkEntityManagerProvider;
import enkan.component.flyway.FlywayMigration;
import enkan.component.hikaricp.HikariCPComponent;
import enkan.component.jackson.JacksonBeansConverter;
import enkan.component.jetty.JettyComponent;
import enkan.config.EnkanSystemFactory;
import enkan.system.EnkanSystem;
import kotowari.restful.component.BeansValidator;
import net.unit8.bouncr.sign.JsonWebToken;
import net.unit8.rascaloid.entity.*;

import static enkan.component.ComponentRelationship.component;
import static enkan.util.BeanBuilder.builder;

public class RascaloidSystemFactory implements EnkanSystemFactory {
    @Override
    public EnkanSystem create() {
        return EnkanSystem.of(
                "jwt", new JsonWebToken(),
                "jackson", new JacksonBeansConverter(),
                "flyway", new FlywayMigration(),
                "jpa", builder(new EclipseLinkEntityManagerProvider())
                        .set(EclipseLinkEntityManagerProvider::setName, "rascaloid")
                        .set(EclipseLinkEntityManagerProvider::registerClass, Project.class)
                        .set(EclipseLinkEntityManagerProvider::registerClass, Story.class)
                        .set(EclipseLinkEntityManagerProvider::registerClass, Task.class)
                        .set(EclipseLinkEntityManagerProvider::registerClass, TaskStatus.class)
                        .set(EclipseLinkEntityManagerProvider::registerClass, Iteration.class)
                        .set(EclipseLinkEntityManagerProvider::registerClass, IterationPlan.class)
                        .build(),
                "validator", new BeansValidator(),
                "datasource", new HikariCPComponent(OptionMap.of(
                        "uri", Env.getString("JDBC_URL", "jdbc:h2:mem:test;AUTOCOMMIT=FALSE;DB_CLOSE_DELAY=-1"),
                        "username", Env.getString("JDBC_USER", ""),
                        "password", Env.getString("JDBC_PASS", "")
                )),
                "app", new ApplicationComponent("net.unit8.rascaloid.RascaloidApplicationFactory"),
                "http", builder(new JettyComponent())
                        .set(JettyComponent::setPort, Env.getInt("PORT", 3000))
                        .build()

        ).relationships(
                component("http").using("app"),
                component("app").using(
                        "datasource", "validator", "jpa", "jackson", "jwt"),
                component("jpa").using("datasource", "flyway"),
                component("flyway").using("datasource")
        );
    }
}
