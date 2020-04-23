package com.vnl.web.presentation;

import com.vnl.web.presentation.response.BookResponse;
import com.vnl.web.usecase.BookUseCase;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Bookコントローラクラスです。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookUseCase bookUseCase;

    /**
     * Bookリストのモデルビューを取得します。
     *
     * @return Bookモデルビュー
     */
    @GetMapping("/view")
    @ModelAttribute("beforeMonth")
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
     * Bookリストを取得します。
     *
     * @return Bookレスポンス
     */
    @GetMapping("/")
    public ResponseEntity<List<BookResponse>> findAll() {
        final List<BookResponse> books =
            bookUseCase.findAll()
                .stream()
                .map(v -> new BookResponse(v.title(), v.isbn(), v.publishDate(), v.daysAgo()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
