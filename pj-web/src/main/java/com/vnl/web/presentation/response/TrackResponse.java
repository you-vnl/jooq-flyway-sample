package com.vnl.web.presentation.response;

import lombok.Value;

/**
 * トラック
 */
@Value
class TrackResponse {
    /**
     * トラックID
     */
    String trackId;

    /**
     * トラック名
     */
    String trackName;

    /**
     * トラック検閲名
     */
    String trackCensoredName;

    /**
     * トラック閲覧URL
     */
    String trackViewUrl;

    /**
     * トラック価格
     */
    int trackPrice;

    /**
     * トラック ペアレンタルコントロール
     */
    String trackExplicitness;

    /**
     * トラック番号
     */
    int trackNumber;

    /**
     * トラック時間（ミリ秒）
     */
    int trackTimeMillis;

    /**
     * トラック時刻（文字列）
     */
    String trackTimeString;

}