package com.vnl.web.presentation.response;

import java.time.LocalDate;
import lombok.Value;

/**
 * BookのResponseクラスです。
 */
@Value
public class BookResponse {


    /**
     * ISBN
     */
    String isbn;

    /**
     * タイトル
     */
    String title;

    /**
     * 公開日
     */
    LocalDate publishDate;

    /**
     * 経過日数
     */
    long daysAgo;
}
