package net.unit8.rascaloid;

import com.zaxxer.hikari.HikariConfig;
import enkan.Env;
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
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.seasar.doma.jdbc.dialect.PostgresDialect;

import static enkan.component.ComponentRelationship.component;
import static enkan.util.BeanBuilder.builder;

public class RascaloidSystemFactory implements EnkanSystemFactory {
    @Override
    public EnkanSystem create() {
        HikariConfig hikariConfig = builder(new HikariConfig())
                .set(HikariConfig::setJdbcUrl,  Env.getString("JDBC_URL", "jdbc:h2:mem:test"))
                .set(HikariConfig::setUsername, Env.getString("JDBC_USER", ""))
                .set(HikariConfig::setPassword, Env.getString("JDBC_PASS", ""))
                .set(HikariConfig::setInitializationFailTimeout, 30_000L)
                .build();

        return EnkanSystem.of(
                "doma", builder(new DomaProvider())
                        .set(DomaProvider::setDialect, detectDialect())
                        .set(DomaProvider::setNaming, Naming.SNAKE_LOWER_CASE)
                        .build(),
                "jwt", new JsonWebToken(),
                "jackson", new JacksonBeansConverter(),
                "flyway", new FlywayMigration(),
                "template", new FreemarkerTemplateEngine(),
                "datasource", builder(new HikariCPComponent())
                        .set(HikariCPComponent::setConfig, hikariConfig)
                        .build(),
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
