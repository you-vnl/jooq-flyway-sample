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

    /**
     * Bookリストを検索します。
     *
     * @return Bookリスト
     */
    public List<Book> findBookList() {
        return findAll()
            .stream()
            .map(bookRecord -> new Book(bookRecord.getTitle(), bookRecord.getIsbn(), bookRecord.getPublishDate().toLocalDate()))
            .collect(Collectors.toList());
    }


    /**
     * BookRecordリストを検索します。<br> jOOQのRecord型はリポジトリレイヤに隠蔽するため非publicとしています。
     *
     * @return BookRecordリスト
     */
    List<BookRecord> findAll() {
        return create
            .select(BookTable.BOOK.ISBN,
                BookTable.BOOK.TITLE,
                BookTable.BOOK.PUBLISH_DATE)
            .from(BookTable.BOOK)
            .orderBy(BookTable.BOOK.PUBLISH_DATE)
            .fetchInto(BookRecord.class);
    }
}
