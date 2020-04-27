package com.vnl.web.domain.repository;

import static com.vnl.db.jooq.gen.tables.BookTable.BOOK;

import com.vnl.db.jooq.gen.tables.pojos.BookEntity;
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
     * Bookの登録を行います。
     */
    public void upsert(final BookEntity entity) {
        create.insertInto(BOOK,
            BOOK.ISBN,
            BOOK.TITLE,
            BOOK.PUBLISH_DATE)
            .values(entity.getIsbn(),
                entity.getTitle(),
                entity.getPublishDate())
            .onDuplicateKeyUpdate()
            .set(create.newRecord(BOOK, entity))
            .execute();
    }
}
