package com.vnl.web.repository;

import com.vnl.db.jooq.gen.tables.BookTable;
import com.vnl.db.jooq.gen.tables.records.BookRecord;
import com.vnl.web.domain.model.Book;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * Bookリポジトリクラス
 */
@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final DSLContext create;

    public List<Book> findAll() {
        final BookTable bookTable = BookTable.BOOK;

        final List<BookRecord> selected = create
            .select(bookTable.ISBN,
                bookTable.TITLE,
                bookTable.PUBLISH_DATE)
            .from(bookTable)
            .orderBy(bookTable.PUBLISH_DATE)
            .fetchInto(BookRecord.class);

        return selected
            .stream()
            .map(bookRecord -> new Book(bookRecord.getTitle(), bookRecord.getIsbn(), bookRecord.getPublishDate().toLocalDate()))
            .collect(Collectors.toList());
    }
}
