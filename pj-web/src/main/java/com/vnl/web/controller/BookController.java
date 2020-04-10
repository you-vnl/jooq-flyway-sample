package com.vnl.web.controller;

import com.vnl.web.usecase.BookUseCase;
import com.vnl.web.usecase.BookUseCase.BookDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
        final List<BookDto> books = bookUseCase.findAll();
        model.addAttribute("books", books);
        return "index";
    }
}
