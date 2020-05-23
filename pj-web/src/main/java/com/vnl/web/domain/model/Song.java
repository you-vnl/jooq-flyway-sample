package com.vnl.web.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 楽曲情報の集約ルートクラス
 */
@NoArgsConstructor
@Getter
public class Song {

    /**
     * トラック
     */
    private Track track;

    /**
     * アーティスト
     */
    private Artist artist;

    /**
     * アルバム
     */
    private Album album;

    /**
     * 楽曲属性
     */
    private SongAttribute songAttribute;

    /**
     * トラック情報を付与します。
     */
    public void attachTrack(final String trackId, final String trackName, final String trackCensoredName, final String trackViewUrl, final int trackPrice, final String trackExplicitness,
        final int trackNumber, final int trackTimeMillis,
        final String trackTimeString) {
        track = new Track(trackId, trackName, trackCensoredName, trackViewUrl, trackPrice, trackExplicitness, trackNumber, trackTimeMillis, trackTimeString);
    }

    /**
     * アーティスト情報を付与します。
     */
    public void attachArtist(final int artistId, final String artistName, final String artistViewUrl) {
        artist = new Artist(artistId, artistName, artistViewUrl);
    }

    /**
     * アルバム情報を付与します。
     */
    public void attachAlbum(final String collectionId, final String collectionName, final String collectionCensoredName, final int collectionPrice, final String collectionViewUrl) {
        album = new Album(collectionId, collectionName, collectionCensoredName, collectionPrice, collectionViewUrl);
    }

    /**
     * アルバムアーティスト情報を付与します。
     */
    public void attachAlbumArtist(final int artistId, final String artistName, final String artistViewUrl) {
        album.attachAlbumArtist(artistId, artistName, artistViewUrl);
    }

    /**
     * 楽曲属性情報を付与します。
     */
    public void attachSongAttribute(final String kind, final String previewUrl, final int discCount, final int discNumber, final boolean isStreamable, final String releaseDate,
        final String artworkUrl30, final String artworkUrl60, final String artworkUrl100, final String collectionExplicitness, final String country, final String currency,
        final String primaryGenreName, final int trackCount,
        final String wrapperType) {
        songAttribute = new SongAttribute(kind, previewUrl, discCount, discNumber, isStreamable, releaseDate, artworkUrl30, artworkUrl60, artworkUrl100, collectionExplicitness, country, currency,
            primaryGenreName, trackCount, wrapperType);
    }
}
