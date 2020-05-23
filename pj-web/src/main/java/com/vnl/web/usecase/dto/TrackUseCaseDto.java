package com.vnl.web.usecase.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * トラック
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TrackUseCaseDto {
    /**
     * トラックID
     */
    private final String trackId;

    /**
     * トラック名
     */
    private final String trackName;

    /**
     * トラック検閲名
     */
    private final String trackCensoredName;

    /**
     * トラック閲覧URL
     */
    private final String trackViewUrl;

    /**
     * トラック価格
     */
    private final int trackPrice;

    /**
     * トラック ペアレンタルコントロール
     */
    private final String trackExplicitness;

    /**
     * トラック番号
     */
    private final int trackNumber;

    /**
     * トラック時間（ミリ秒）
     */
    private final int trackTimeMillis;

    /**
     * トラック時刻（文字列）
     */
    private final String trackTimeString;

}