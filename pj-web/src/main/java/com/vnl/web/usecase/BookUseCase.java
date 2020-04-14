package com.vnl.web.usecase;

import com.vnl.web.repository.BookRepository;
import java.time.LocalDate;
import java.util.List;
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
     * Bookを検索します。
     *
     * @return 全てのBookリスト
     */
    public List<BookDto> findAll() {
        return bookRepository.findAll()
            .stream()
            .map(v -> new BookDto(v.getTitle(), v.getIsbn(), v.getPublishDate(), v.getDaysAgo()))
            .collect(Collectors.toList());
    }

    /**
     * BookのDtoクラスです。
     */
    public record BookDto(String title, String isbn, LocalDate publishDate, long daysAgo) {

    }

}
