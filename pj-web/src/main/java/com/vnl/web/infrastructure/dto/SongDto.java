package com.vnl.web.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vnl.web.domain.model.Song;
import lombok.Data;

/**
 * 楽曲詳細
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SongDto {

    /**
     * コンテンツの種類
     */
    private String kind;

    /**
     * アルバムアーティストID
     */
    private int collectionArtistId;

    /**
     * アルバムアーティスト名
     */
    private String collectionArtistName;

    /**
     * アルバムアーティスト閲覧URL
     */
    private String collectionArtistViewUrl;

    /**
     * プレビューURL
     */
    private String previewUrl;

    /**
     * アートワークURL 30サイズ
     */
    private String artworkUrl30;

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

    //song info

    /**
     * 発売日
     */
    String releaseDate;

    /**
     * アーティストID
     */
    private int artistId;

    /**
     * アーティスト名
     */
    private String artistName;

    /**
     * アーティストURL
     */
    private String artistViewUrl;

    /**
     * 楽曲アートワークURL 100サイズ
     */
    private String artworkUrl100;

    /**
     * 楽曲アートワークURL 60サイズ
     */
    private String artworkUrl60;

    /**
     * アルバム名
     */
    private String collectionCensoredName;

    /**
     * ペアレンタルコントロール
     */
    private String collectionExplicitness;

    /**
     * アルバムID
     */
    private String collectionId;

    /**
     * アルバム名
     */
    private String collectionName;

    /**
     * アルバム価格
     */
    private int collectionPrice;

    /**
     * アルバム閲覧URL
     */
    private String collectionViewUrl;

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
     * トラック番号
     */
    private int trackCount;

    /**
     * オブジェクトの種類（track, collection, artist）
     */
    private String wrapperType;


    /**
     * Dto -> モデルへの変換を行います。
     */
    public static Song convertToModel(final SongDto dto) {
        final Song song = new Song();
        song.attachTrack(dto.getTrackId(), dto.getTrackName(), dto.getTrackCensoredName(), dto.getTrackViewUrl(), dto.getTrackPrice(), dto.getTrackExplicitness(), dto.getTrackNumber(),
            dto.getTrackTimeMillis(), dto.getTrackTimeString());
        song.attachArtist(dto.getArtistId(), dto.getArtistName(), dto.getArtistViewUrl());
        song.attachAlbum(dto.getCollectionId(), dto.getCollectionName(), dto.getCollectionCensoredName(), dto.getCollectionPrice(), dto.getCollectionViewUrl());
        song.attachAlbumArtist(dto.getArtistId(), dto.getCollectionArtistName(), dto.getArtistViewUrl());
        song.attachSongAttribute(dto.getKind(), dto.getPreviewUrl(), dto.getDiscCount(), dto.getDiscNumber(), dto.isStreamable(), dto.getReleaseDate(), dto.getArtworkUrl30(), dto.getArtworkUrl60(),
            dto.getArtworkUrl100(), dto.getCollectionExplicitness(), dto.getCountry(), dto.getCurrency(), dto.getPrimaryGenreName(), dto.getTrackCount(), dto.getWrapperType());
        return song;
    }

}
