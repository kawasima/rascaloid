package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.Statement;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class V4__CreateIteration implements JdbcMigration{

    @Override
    public void migrate(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("iterations"))
                    .column(field("iteration_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("project_id", SQLDataType.BIGINT.nullable(false)))
                    .column(field("subject", SQLDataType.VARCHAR(100).nullable(false)))
                    .column(field("description", SQLDataType.CLOB.nullable(false)))
                    .column(field("start_on", SQLDataType.DATE.nullable(false)))
                    .column(field("end_on", SQLDataType.DATE.nullable(false)))
                    .constraints(
                            constraint().primaryKey(field("iteration_id")),
                            constraint().foreignKey(field("project_id")).references(table("projects"), field("project_id"))
                    ).getSQL();
            stmt.execute(ddl);
        }

    }
}
