package com.vnl.web.presentation.request;

import java.time.LocalDate;
import lombok.Value;

/**
 * BookのRequestクラスです。
 */
@Value
public class BookRequest {

    /**
     * タイトル
     */
    String title;

    /**
     * ISBN
     */
    String isbn;

    /**
     * 公開日
     */
    LocalDate publishDate;
}
