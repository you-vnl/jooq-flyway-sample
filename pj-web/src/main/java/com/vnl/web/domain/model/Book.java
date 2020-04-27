package com.vnl.web.domain.model;

import java.time.Duration;
import java.time.LocalDate;
import lombok.Value;

/**
 * Bookのモデルクラス
 */
@Value
public class Book {

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
     * 経過日数を算出して返します。
     *
     * @return 経過日数
     */
    public long getDaysAgo() {
        return Duration.between(publishDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays();
    }

}
