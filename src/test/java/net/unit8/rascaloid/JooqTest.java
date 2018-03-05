package net.unit8.rascaloid;

import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.SQLDataType;
import org.junit.jupiter.api.Test;

import static org.jooq.impl.DSL.*;

public class JooqTest {
    @Test
    public void test() throws Exception {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        try (DSLContext create = DSL.using(ds.getConnection())) {
            String sql = create.insertInto(table("hoge"))
                    .columns(field("c1"), field("c2"))
                    .values(param(), param(SQLDataType.INTEGER))
                    .getSQL();
            System.out.println(sql);
        }
    }
}
