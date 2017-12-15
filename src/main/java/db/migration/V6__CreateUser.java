package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Connection;
import java.sql.Statement;

import static org.jooq.impl.DSL.*;

public class V6__CreateUser implements JdbcMigration {

    @Override
    public void migrate(Connection connection) throws Exception {
        try (Statement stmt = connection.createStatement()) {
            DSLContext create = DSL.using(connection);
            String ddl = create.createTable(table("users"))
                    .column(field("user_id", SQLDataType.BIGINT.identity(true)))
                    .column(field("account", SQLDataType.VARCHAR(30).nullable(false)))
                    .constraints(
                            constraint().primaryKey(field("user_id")),
                            constraint().unique(field("account"))
                    ).getSQL();
            stmt.execute(ddl);

            ddl = create.createTable(table("memberships"))
                    .column(field("user_id", SQLDataType.BIGINT.nullable(false)))
                    .column(field("project_id", SQLDataType.BIGINT.nullable(false)))
                    .constraints(
                            constraint().primaryKey(field("user_id"), field("project_id")),
                            constraint().foreignKey(field("user_id")).references(table("users"), field("user_id")),
                            constraint().foreignKey(field("project_id")).references(table("projects"), field("project_id"))
                    ).getSQL();
            stmt.execute(ddl);
        }
    }
}
