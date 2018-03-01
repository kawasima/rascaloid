package net.unit8.rascaloid;

import enkan.Env;
import enkan.collection.OptionMap;
import enkan.component.ApplicationComponent;
import enkan.component.doma2.DomaProvider;
import enkan.component.flyway.FlywayMigration;
import enkan.component.freemarker.FreemarkerTemplateEngine;
import enkan.component.hikaricp.HikariCPComponent;
import enkan.component.jackson.JacksonBeansConverter;
import enkan.component.jetty.JettyComponent;
import enkan.config.EnkanSystemFactory;
import enkan.system.EnkanSystem;
import net.unit8.bouncr.sign.JsonWebToken;
import org.seasar.doma.jdbc.Naming;
import org.seasar.doma.jdbc.dialect.*;

import static enkan.component.ComponentRelationship.*;
import static enkan.util.BeanBuilder.*;

public class RascaloidSystemFactory implements EnkanSystemFactory {
    @Override
    public EnkanSystem create() {
        return EnkanSystem.of(
                "doma", builder(new DomaProvider())
                        .set(DomaProvider::setDialect, detectDialect())
                        .set(DomaProvider::setNaming, Naming.SNAKE_LOWER_CASE)
                        .build(),
                "jwt", new JsonWebToken(),
                "jackson", new JacksonBeansConverter(),
                "flyway", new FlywayMigration(),
                "template", new FreemarkerTemplateEngine(),
                "datasource", new HikariCPComponent(OptionMap.of(
                        "uri", Env.getString("JDBC_URL", "jdbc:h2:mem:test"),
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
                        "datasource", "template", "doma", "jackson", "jwt"),
                component("doma").using("datasource", "flyway"),
                component("flyway").using("datasource")
        );
    }

    private Dialect detectDialect() {
        String jdbcUrl=Env.get("JDBC_URL");
        if (jdbcUrl != null) {
            if (jdbcUrl.startsWith("jdbc:h2:")) {
                return new H2Dialect();
            } else if (jdbcUrl.startsWith("jdbc:postgresql:")) {
                return new PostgresDialect();
            } else if (jdbcUrl.startsWith("jdbc:mysql:")) {
                return new MysqlDialect();
            }
        }
        return new H2Dialect();
    }
}
