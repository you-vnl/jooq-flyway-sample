package com.vnl.web.usecase;

import com.vnl.web.repository.BookRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

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
        return bookRepository.selectAll()
            .stream()
            .map(v -> new BookDto(v.getTitle(), v.getIsbn(), v.getPublishDate()))
            .collect(Collectors.toList());
    }

    /**
     * BookのDtoクラスです。
     */
    @Value
    public static class BookDto {

        String title;
        String isbn;
        LocalDate publishDate;

        public long getDaysAgo() {
            return Duration.between(publishDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
        }

    }

}
