package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.Statement;

import static org.jooq.impl.DSL.*;

public class V8__CreateIterationPlan implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("iteration_plans"))
                    .column(field("iteration_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("story_id", SQLDataType.BIGINT.identity(true)))
                    .constraints(
                            constraint().primaryKey(field("iteration_id"), field("story_id")),
                            constraint().foreignKey(field("iteration_id")).references(table("iterations"), field("iteration_id")),
                            constraint().foreignKey(field("story_id")).references(table("stories"), field("story_id"))
                    ).getSQL();
            stmt.execute(ddl);
        }
    }
}
