package com.vnl.web.presentation;

import com.vnl.web.presentation.request.BookRequest;
import com.vnl.web.presentation.response.BookResponse;
import com.vnl.web.usecase.BookUseCase;
import com.vnl.web.usecase.BookUseCase.BookRequestDto;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

/**
 * Bookコントローラクラスです。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookUseCase bookUseCase;

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
                .map(v -> new BookResponse(v.isbn(), v.title(), v.publishDate(), v.daysAgo()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * BookリストをCSV出力します。
     *
     * @return Bookレスポンス
     */
    @GetMapping("/csv")
    public ResponseEntity<byte[]> exportToCsv() throws UnsupportedEncodingException {
        final String bookListCsvString = bookUseCase.exportToCsv();
        return new ResponseEntity<>(bookListCsvString.getBytes("MS932"), createFileResponseHeader(), HttpStatus.OK);
    }

    /**
     * BookリストをCSVからリポジトリへ取り込みます。
     *
     * @return HttpStatus.OK
     */
    @PostMapping("/csv")
    public ResponseEntity<byte[]> importCsv() {
        bookUseCase.importCsvByStorage();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * ストレージファイルリストを取得します。
     *
     * @return ストレージファイルリスト
     */
    @GetMapping("/storage")
    public ResponseEntity<List<String>> getStorageFileList() {
        return new ResponseEntity<>(bookUseCase.getStorageFileList(), HttpStatus.OK);
    }

    /**
     * ファイルレスポンスのヘッダを作成します。
     *
     * @return HttpHeaders
     */
    private static HttpHeaders createFileResponseHeader() {
        final String DELIMITER_STRING = "_";
        final String FILE_IDENTIFIER = "book";
        final String fileName = String.join("", FILE_IDENTIFIER, DELIMITER_STRING,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")), ".csv");

        final String headerValue = """
            attachment; filename="%s"; filename*=UTF-8''%s"""
            .formatted(fileName, UriUtils.encode(fileName, StandardCharsets.UTF_8.name()));

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        return responseHeaders;
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
            .map(v -> new ResponseEntity<>(new BookResponse(v.isbn(), v.title(), v.publishDate(), v.daysAgo()), HttpStatus.OK))
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
                .map(v -> new BookResponse(v.isbn(), v.title(), v.publishDate(), v.daysAgo()))
                .collect(Collectors.toList());

        model.addAttribute("books", books);
        return "index";
    }
}
