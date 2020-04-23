package com.vnl.web.presentation.response;

import java.time.LocalDate;
import lombok.Value;

/**
 * BookのResponseクラスです。
 */
@Value
public class BookResponse {

    String title;
    String isbn;
    LocalDate publishDate;
    long daysAgo;
}
