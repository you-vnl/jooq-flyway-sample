package com.vnl.web.usecase.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 楽曲属性
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SongAttributeUseCaseDto {

    /**
     * コンテンツの種類
     */
    private final String kind;

    /**
     * プレビューURL
     */
    private final String previewUrl;

    /**
     * ディスク枚数
     */
    private final int discCount;

    /**
     * ディスク番号
     */
    private final int discNumber;

    /**
     * ストリーミング可能
     */
    private final boolean isStreamable;

    /**
     * 発売日
     */
    private final String releaseDate;

    /**
     * アートワークURL 30サイズ
     */
    private final String artworkUrl30;

    /**
     * 楽曲アートワークURL 60サイズ
     */
    private final String artworkUrl60;

    /**
     * 楽曲アートワークURL 100サイズ
     */
    private final String artworkUrl100;

    /**
     * ペアレンタルコントロール
     */
    private final String collectionExplicitness;

    /**
     * 国
     */
    private final String country;

    /**
     * 貨幣
     */
    private final String currency;

    /**
     * ジャンル名
     */
    private final String primaryGenreName;

    /**
     * トラック数
     */
    private final int trackCount;

    /**
     * オブジェクトの種類（track, collection, artist）
     */
    private final String wrapperType;
}
