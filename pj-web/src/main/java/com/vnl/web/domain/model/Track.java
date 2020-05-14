package com.vnl.web.domain.model;

import lombok.Value;

/**
 * トラック
 */
@Value
class Track {
    /**
     * トラックID
     */
    private String trackId;

    /**
     * トラック名
     */
    private String trackName;

    /**
     * トラック検閲名
     */
    private String trackCensoredName;

    /**
     * トラック閲覧URL
     */
    private String trackViewUrl;

    /**
     * トラック価格
     */
    private int trackPrice;

    /**
     * トラック ペアレンタルコントロール
     */
    private String trackExplicitness;

    /**
     * トラック番号
     */
    private int trackNumber;

    /**
     * トラック時間（ミリ秒）
     */
    private int trackTimeMillis;

    /**
     * トラック時刻（文字列）
     */
    private String trackTimeString;
}