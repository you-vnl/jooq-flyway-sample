package testdata;

import static org.jooq.impl.DSL.using;

import com.vnl.db.jooq.gen.tables.BookTable;
import com.vnl.db.jooq.gen.tables.records.BookRecord;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;

public class R__ImportMoreBookWithJooq extends BaseJavaMigration {

    @Override
    public void migrate(final Context context) {

        final int NUMBER_OF_RECORDS = 10;

        final DSLContext create = using(context.getConnection(), SQLDialect.POSTGRES);
        final LocalDate baseDate = LocalDate.of(2010, 1, 1);

        IntStream.range(0, NUMBER_OF_RECORDS)
            .forEach(i -> {
                final BookRecord rec = create.newRecord(BookTable.BOOK);
                rec.setIsbn("isbn-test-with-jooq-" + i);
                rec.setTitle("test title with jooq " + i);
                rec.setPublishDate(java.sql.Date.valueOf(baseDate.plusDays(i)));
                rec.store();
            });
    }
}
