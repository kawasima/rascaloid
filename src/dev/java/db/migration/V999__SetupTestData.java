package db.migration;

import org.flywaydb.core.api.migration.jdbc.JdbcMigration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.jooq.impl.DSL.*;

public class V999__SetupTestData implements JdbcMigration {
    private Long fetchGeneratedKey(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if(rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("Generated key is not found.");
        }
    }

    @Override
    public void migrate(Connection connection) throws Exception {
        DSLContext create = DSL.using(connection);
        final String INSERT_PROJECT = create.insertInto(table("projects"))
                .columns(field("name"), field("description"))
                .values(param(), param())
                .getSQL();

        final String INSERT_ITERATION = create.insertInto(table("iterations"))
                .columns(field("project_id"), field("subject"), field("description"), field("start_on"), field("end_on"))
                .values(param(), param(), param(), param(), param())
                .getSQL();

        final String INSERT_STORY = create.insertInto(table("stories"))
                .columns(field("project_id"), field("subject"), field("description"), field("point"))
                .values(param(), param(), param(), param())
                .getSQL();

        final String INSERT_DEVELOPMENT_TASK = create.insertInto(table("development_tasks"))
                .columns(field("task_id"), field("story_id"))
                .values(param(), param())
                .getSQL();

        final String INSERT_TASK_STATUS = create.insertInto(table("task_status"))
                .columns(field("name"), field("position"))
                .values(param(), param())
                .getSQL();

        try(PreparedStatement projectStmt = connection.prepareStatement(INSERT_PROJECT, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement iterationStmt = connection.prepareStatement(INSERT_ITERATION, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement taskStatusStmt = connection.prepareStatement(INSERT_TASK_STATUS, Statement.RETURN_GENERATED_KEYS)) {
            projectStmt.setString(1, "テストプロジェクト");
            projectStmt.setString(2, "これはテストです");
            projectStmt.executeUpdate();
            Long projectId = fetchGeneratedKey(projectStmt);

            iterationStmt.setLong(1, projectId);
            iterationStmt.setString(2, "LAST GIGS");
            iterationStmt.setString(3, "最後の夜");
            iterationStmt.setDate(4, new java.sql.Date(LocalDate.of(2017, 9, 1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)));
            iterationStmt.setDate(5, new java.sql.Date(LocalDate.of(2020, 9, 1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)));
            Long iterationId = fetchGeneratedKey(iterationStmt);

            taskStatusStmt.setString(1, "TODO");
            taskStatusStmt.setLong(2, 1L);
            taskStatusStmt.executeUpdate();

            taskStatusStmt.setString(1, "Doing");
            taskStatusStmt.setLong(2, 2L);
            taskStatusStmt.executeUpdate();

            taskStatusStmt.setString(1, "Done");
            taskStatusStmt.setLong(2, 3L);
            taskStatusStmt.executeUpdate();

            connection.commit();
        }
    }
}
