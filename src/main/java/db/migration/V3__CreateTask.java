package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.jooq.impl.DSL.*;

public class V3__CreateTask implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("tasks"))
                    .column(field("task_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("project_id", SQLDataType.BIGINT.nullable(false)))
                    .column(field("subject", SQLDataType.VARCHAR(100).nullable(false)))
                    .column(field("description", SQLDataType.CLOB.nullable(false)))
                    .column(field("estimated_hours", SQLDataType.INTEGER))
                    .constraints(
                            constraint().primaryKey(field("task_id")),
                            constraint().foreignKey(field("project_id")).references(table("projects"), field("project_id"))
                    ).getSQL();
            stmt.execute(ddl);

            createDevelopmentTask(stmt);
        }
    }

    private void createDevelopmentTask(Statement stmt) throws SQLException {
        DSLContext create = DSL.using(stmt.getConnection());
        String ddl = create.createTable(table("development_tasks"))
                .column(field("task_id", SQLDataType.BIGINT.nullable(false)))
                .column(field("story_id", SQLDataType.BIGINT.nullable(false)))
                .column(field("status_id", SQLDataType.BIGINT.nullable(false)))
                .constraints(
                        constraint().primaryKey(field("task_id")),
                        constraint().foreignKey(field("task_id")).references(table("tasks"), field("task_id")),
                        constraint().foreignKey(field("story_id")).references(table("stories"), field("story_id"))
                )
                .getSQL();
        stmt.execute(ddl);
    }
}
