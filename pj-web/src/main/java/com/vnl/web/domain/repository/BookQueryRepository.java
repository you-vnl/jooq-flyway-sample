package com.vnl.web.domain.repository;

import static com.vnl.db.jooq.gen.tables.BookTable.BOOK;

import com.vnl.db.jooq.gen.tables.records.BookRecord;
import com.vnl.web.domain.model.Book;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

/**
 * Bookクエリリポジトリクラス
 */
@Repository
@RequiredArgsConstructor
public class BookQueryRepository {

    private final DSLContext create;

    /**
     * Bookリストを検索します。
     *
     * @return Bookリスト
     */
    public List<Book> findBookList() {
        return findAll()
            .stream()
            .map(bookRecord -> new Book(bookRecord.getTitle(), bookRecord.getIsbn(), bookRecord.getPublishDate()))
            .collect(Collectors.toList());
    }

    /**
     * Bookリストを検索します。
     *
     * @return Bookリスト
     */
    public Optional<Book> findById(final String id) {
        return create
            .select(BOOK.ISBN,
                BOOK.TITLE,
                BOOK.PUBLISH_DATE)
            .from(BOOK)
            .where(BOOK.ISBN.eq(id))
            .orderBy(BOOK.PUBLISH_DATE)
            .fetchOptionalInto(BookRecord.class)
            .map(bookRecord -> new Book(bookRecord.getTitle(), bookRecord.getIsbn(), bookRecord.getPublishDate()));
    }


    /**
     * BookRecordリストを検索します。<br> jOOQのRecord型はリポジトリレイヤに隠蔽するため非publicとしています。
     *
     * @return BookRecordリスト
     */
    List<BookRecord> findAll() {
        return create
            .select(BOOK.ISBN,
                BOOK.TITLE,
                BOOK.PUBLISH_DATE)
            .from(BOOK)
            .orderBy(BOOK.PUBLISH_DATE)
            .fetchInto(BookRecord.class);
    }

    /**
     * BookRecordリストをCSV文字列として返します。(最大200件まで)
     *
     * @return BookRecordリストのCSV文字列
     */
    public String findAllToCsvString() {
        return create
            .select(BOOK.ISBN,
                BOOK.TITLE,
                BOOK.PUBLISH_DATE)
            .from(BOOK)
            .orderBy(BOOK.PUBLISH_DATE)
            .limit(200)
            .fetch()
            .formatCSV();
    }

}
