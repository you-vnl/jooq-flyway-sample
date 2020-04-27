package com.vnl.web.presentation.response;

import java.time.LocalDate;
import lombok.Value;

/**
 * BookのResponseクラスです。
 */
@Value
public class BookResponse {

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

    /**
     * 経過日数
     */
    long daysAgo;
}
