package com.vnl.web.usecase;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.vnl.db.jooq.gen.tables.pojos.BookEntity;
import com.vnl.web.StorageConfig;
import com.vnl.web.domain.repository.BookQueryRepository;
import com.vnl.web.domain.repository.BookRepository;
import com.vnl.web.util.FileOperationUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bookユースケースクラスです。
 */
@Controller
@RequiredArgsConstructor
@Transactional
public class BookUseCase {

    private final BookRepository bookRepository;

    private final BookQueryRepository bookQueryRepository;

    private final StorageConfig storageConfig;

    /**
     * Bookのリストを検索します。
     *
     * @return 全てのBookリスト
     */
    public List<BookResponseDto> findAll() {
        return bookQueryRepository.findBookList()
            .stream()
            .map(v -> new BookResponseDto(v.getIsbn(), v.getTitle(), v.getPublishDate(), v.getDaysAgo()))
            .collect(Collectors.toList());
    }

    /**
     * BookのリストをCSV文字列にします。
     *
     * @return 全てのBookリスト
     */
    public String exportToCsv() {
        final List<BookExportationDto> list = bookQueryRepository.findBookList()
            .stream()
            .map(v -> new BookExportationDto(v.getIsbn(), v.getTitle(), v.getPublishDate()))
            .collect(Collectors.toList());

        return FileOperationUtils.convertListToCsvString(list, BookExportationDto.class);
    }

    /**
     * CSVファイルの取り込みをストレージから行います。
     */
    public void importCsvByStorage() {
        getStorageFileList()
            .stream()
            .map(storageConfig::getStorageObject)
            .forEach(this::registerByStorageFile);
    }

    /**
     * ストレージ上のファイルからBookの登録を行います。一時ファイルとしてダウンロードし、登録処理終了後に削除を行います。
     *
     * @param s3Object ストレージ上のファイル
     */
    private void registerByStorageFile(final S3Object s3Object) {
        Optional<File> tempFile = Optional.empty();
        try {
            tempFile = Optional.of(File.createTempFile(s3Object.getKey(), ".csv"));
            FileOperationUtils.convertS3ObjectToFile(s3Object, tempFile.get());

            FileOperationUtils.readCsvFileToList(Paths.get(tempFile.get().getAbsolutePath()), BookExportationDto.class)
                .stream()
                .map(v -> new BookEntity(v.isbn, v.title, v.publishDate))
                .forEach(bookRepository::upsert);

        } catch (final IOException e) {
            throw new RuntimeException("importCsv TempFile Error.", e);
        } finally {
            tempFile.filter(File::exists).ifPresent(File::delete);
        }
    }

    /**
     * ストレージファイルリストを返します。
     *
     * @return ストレージファイルリスト
     */
    public List<String> getStorageFileList() {
        final ObjectListing objListing = storageConfig.createStorageClient().listObjects(storageConfig.getBucketName());

        return objListing.getObjectSummaries().stream()
            .map(S3ObjectSummary::getKey)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * BookをIDから検索します。
     *
     * @return 全てのBookリスト
     */
    public Optional<BookResponseDto> findById(final String id) {
        return bookQueryRepository.findById(id)
            .map(v -> new BookResponseDto(v.getIsbn(), v.getTitle(), v.getPublishDate(), v.getDaysAgo()));
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
    public record BookResponseDto(String isbn, String title, LocalDate publishDate, Long daysAgo) {
    }

    /**
     * BookRequestのDtoクラスです。
     */
    public record BookRequestDto(String isbn, String title, LocalDate publishDate) {
    }

    /**
     * Bookの出力用Dtoクラスです。
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonPropertyOrder({"isbn", "title", "publishDate"})
    public static class BookExportationDto {

        String isbn;

        String title;

        LocalDate publishDate;
    }


}
