package com.vnl.web.domain.model;

import lombok.Value;

/**
 * 楽曲属性
 */
@Value
public class SongAttribute {

    /**
     * コンテンツの種類
     */
    private String kind;

    /**
     * プレビューURL
     */
    private String previewUrl;

    /**
     * ディスク枚数
     */
    private int discCount;

    /**
     * ディスク番号
     */
    private int discNumber;

    /**
     * ストリーミング可能
     */
    private boolean isStreamable;

    /**
     * 発売日
     */
    String releaseDate;

    /**
     * アートワークURL 30サイズ
     */
    private String artworkUrl30;

    /**
     * 楽曲アートワークURL 60サイズ
     */
    private String artworkUrl60;

    /**
     * 楽曲アートワークURL 100サイズ
     */
    private String artworkUrl100;

    /**
     * ペアレンタルコントロール
     */
    private String collectionExplicitness;

    /**
     * 国
     */
    private String country;

    /**
     * 貨幣
     */
    private String currency;

    /**
     * ジャンル名
     */
    private String primaryGenreName;

    /**
     * トラック数
     */
    private int trackCount;

    /**
     * オブジェクトの種類（track, collection, artist）
     */
    private String wrapperType;
}
