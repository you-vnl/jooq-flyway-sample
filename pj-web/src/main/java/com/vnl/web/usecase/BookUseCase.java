package com.vnl.web.usecase;

import com.vnl.web.repository.BookRepository;
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

    /**
     * Bookのリストを検索します。
     *
     * @return 全てのBookリスト
     */
    public List<BookDto> findAll() {
        return bookRepository.findBookList()
            .stream()
            .map(v -> new BookDto(v.getTitle(), v.getIsbn(), v.getPublishDate(), v.getDaysAgo()))
            .collect(Collectors.toList());
    }

    /**
     * BookをIDから検索します。
     *
     * @return 全てのBookリスト
     */
    public Optional<BookDto> findById(final String id) {
        return bookRepository.findById(id)
            .map(v -> new BookDto(v.getTitle(), v.getIsbn(), v.getPublishDate(), v.getDaysAgo()));
    }


    /**
     * BookのDtoクラスです。
     */
    public record BookDto(String title, String isbn, LocalDate publishDate, long daysAgo) {

    }

}
