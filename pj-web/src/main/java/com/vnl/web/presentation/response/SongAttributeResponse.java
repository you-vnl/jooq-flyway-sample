package com.vnl.web.presentation.response;

import lombok.Value;

/**
 * 楽曲属性
 */
@Value
public class SongAttributeResponse {

    /**
     * コンテンツの種類
     */
    String kind;

    /**
     * プレビューURL
     */
    String previewUrl;

    /**
     * ディスク枚数
     */
    int discCount;

    /**
     * ディスク番号
     */
    int discNumber;

    /**
     * ストリーミング可能
     */
    boolean isStreamable;

    /**
     * 発売日
     */
    String releaseDate;

    /**
     * アートワークURL 30サイズ
     */
    String artworkUrl30;

    /**
     * 楽曲アートワークURL 60サイズ
     */
    String artworkUrl60;

    /**
     * 楽曲アートワークURL 100サイズ
     */
    String artworkUrl100;

    /**
     * ペアレンタルコントロール
     */
    String collectionExplicitness;

    /**
     * 国
     */
    String country;

    /**
     * 貨幣
     */
    String currency;

    /**
     * ジャンル名
     */
    String primaryGenreName;

    /**
     * トラック数
     */
    int trackCount;

    /**
     * オブジェクトの種類（track, collection, artist）
     */
    String wrapperType;
}
