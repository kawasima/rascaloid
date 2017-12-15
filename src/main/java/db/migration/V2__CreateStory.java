package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.Statement;

import static org.jooq.impl.DSL.*;

public class V2__CreateStory implements JdbcMigration {

    @Override
    public void migrate(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("stories"))
                    .column(field("story_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("project_id", SQLDataType.BIGINT.nullable(false)))
                    .column(field("subject", SQLDataType.VARCHAR(100).nullable(false)))
                    .column(field("description", SQLDataType.CLOB.nullable(false)))
                    .column(field("point", SQLDataType.DECIMAL.nullable(false)))
                    .constraints(
                            constraint().primaryKey(field("story_id")),
                            constraint().foreignKey(field("project_id")).references(table("projects"), field("project_id"))
                    ).getSQL();
            stmt.execute(ddl);
        }
    }
}
