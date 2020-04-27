package com.vnl.web.presentation;

import com.vnl.web.presentation.request.BookRequest;
import com.vnl.web.presentation.response.BookResponse;
import com.vnl.web.usecase.BookUseCase;
import com.vnl.web.usecase.BookUseCase.BookRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * BookをIDから取得します。
     *
     * @param id ID
     * @return Bookレスポンス
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findById(@PathVariable("id") final String id) {
        return bookUseCase.findById(id)
            .map(v -> new ResponseEntity<>(new BookResponse(v.title(), v.isbn(), v.publishDate(), v.daysAgo()), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Bookの登録を行います。
     *
     * @param request bookリクエストパラメータ
     * @return CREATED(201, " Created ")
     */
    @PostMapping
    public ResponseEntity<HttpStatus> register(final BookRequest request) {
        bookUseCase.register(new BookRequestDto(request.getTitle(), request.getIsbn(), request.getPublishDate()));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
