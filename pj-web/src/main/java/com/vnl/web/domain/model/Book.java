package com.vnl.web.domain.model;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Value;

/**
 * Bookのモデルクラスです。
 */
@Value
public class Book {

    String title;
    String isbn;
    LocalDate publishDate;

    public long getDaysAgo() {
        return Duration.between(publishDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
    }

}
