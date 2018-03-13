package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.Statement;

import static org.jooq.impl.DSL.*;

public class V10__CreateTaskLog implements JdbcMigration {
    @Override
    public void migrate(Connection connection) throws Exception {
        try(Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("task_logs"))
                    .column(field("task_log_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("task_id", SQLDataType.BIGINT.nullable(false)))
                    .column(field("user_id", SQLDataType.BIGINT.nullable(false)))
                    .column(field("spend_on", SQLDataType.DATE.nullable(false)))
                    .column(field("hours", SQLDataType.NUMERIC(2,1).nullable(false)))
                    .constraints(
                            constraint().foreignKey(field("task_id")).references(table("tasks"), field("task_id")),
                            constraint().foreignKey(field("user_id")).references(table("users"), field("user_id"))
                    )
                    .toString();
            stmt.execute(ddl);
        }
    }
}
