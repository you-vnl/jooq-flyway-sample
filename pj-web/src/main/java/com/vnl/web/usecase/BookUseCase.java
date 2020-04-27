package com.vnl.web.usecase;

import com.vnl.db.jooq.gen.tables.pojos.BookEntity;
import com.vnl.web.domain.repository.BookQueryRepository;
import com.vnl.web.domain.repository.BookRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bookユースケースクラス
 */
@Controller
@RequiredArgsConstructor
@Transactional
public class BookUseCase {

    private final BookRepository bookRepository;

    private final BookQueryRepository bookQueryRepository;

    /**
     * Bookのリストを検索します。
     *
     * @return 全てのBookリスト
     */
    public List<BookResponseDto> findAll() {
        return bookQueryRepository.findBookList()
            .stream()
            .map(v -> new BookResponseDto(v.getTitle(), v.getIsbn(), v.getPublishDate(), v.getDaysAgo()))
            .collect(Collectors.toList());
    }

    /**
     * BookをIDから検索します。
     *
     * @return 全てのBookリスト
     */
    public Optional<BookResponseDto> findById(final String id) {
        return bookQueryRepository.findById(id)
            .map(v -> new BookResponseDto(v.getTitle(), v.getIsbn(), v.getPublishDate(), v.getDaysAgo()));
    }

    /**
     * Bookの登録を行います。
     */
    public void register(final BookRequestDto book) {
        bookRepository.upsert(new BookEntity(book.isbn, book.title, book.publishDate));
    }

    /**
     * BookResponseのDtoクラスです。
     */
    public record BookResponseDto(String title, String isbn, LocalDate publishDate, Long daysAgo) {

    }

    /**
     * BookRequestのDtoクラスです。
     */
    public record BookRequestDto(String title, String isbn, LocalDate publishDate) {

    }


}
