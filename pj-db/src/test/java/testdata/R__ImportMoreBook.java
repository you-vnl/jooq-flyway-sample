package testdata;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class R__ImportMoreBook extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) {

        final DataSource ds = new SingleConnectionDataSource(context.getConnection(), true);
        final JdbcTemplate tmpl = new JdbcTemplate(ds);

        final LocalDate baseDate = LocalDate.of(1990, 1, 1);

        for (int i = 1; i <= 100; i++) {
            final String isbn = "test-isbn-" + i;
            final String title = "test book " + i;
            final LocalDate publishDate = baseDate.plusDays(i);
            final String sqlString =
                "insert into book (isbn, title, publish_date) "
                    + "values ( '" + isbn + "',"
                    + "'" + title + "'," + "'" + publishDate.toString() + "')";
            tmpl.execute(sqlString);
        }
    }
}
