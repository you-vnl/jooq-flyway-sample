package com.vnl.web.controller;

import com.vnl.web.usecase.BookUseCase;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Bookコントローラクラスです。
 */
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookUseCase bookUseCase;

    @GetMapping("")
    public String index(final Model model) {
        final List<BookResponse> books =
            bookUseCase.findAll()
                .stream()
                .map(v -> new BookResponse(v.title(), v.isbn(), v.publishDate(), v.daysAgo()))
                .collect(Collectors.toList());

        model.addAttribute("books", books);
        return "index";
    }

    /**
     * BookのResponseクラスです。
     */
    @Value
    public static class BookResponse {

        String title;
        String isbn;
        LocalDate publishDate;
        long daysAgo;
    }

}
