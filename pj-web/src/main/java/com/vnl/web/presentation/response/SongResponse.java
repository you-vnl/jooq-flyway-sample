package com.vnl.web.presentation.response;

import com.vnl.web.usecase.dto.SongUseCaseDto;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * 楽曲詳細
 */
@NoArgsConstructor
@Getter
public class SongResponse {

    /**
     * トラック
     */
    private TrackResponse track;

    /**
     * アーティスト
     */
    private ArtistResponse artist;

    /**
     * アルバム
     */
    @Nullable
    private AlbumResponse album;

    /**
     * 楽曲属性
     */
    private SongAttributeResponse songAttribute;

    /**
     * トラック情報を付与します。
     */
    private void attachTrack(final String trackId, final String trackName, final String trackCensoredName, final String trackViewUrl, final int trackPrice, final String trackExplicitness,
        final int trackNumber, final int trackTimeMillis,
        final String trackTimeString) {
        track = new TrackResponse(trackId, trackName, trackCensoredName, trackViewUrl, trackPrice, trackExplicitness, trackNumber, trackTimeMillis, trackTimeString);
    }

    /**
     * アーティスト情報を付与します。
     */
    private void attachArtist(final int artistId, final String artistName, final String artistViewUrl) {
        artist = new ArtistResponse(artistId, artistName, artistViewUrl);
    }

    /**
     * アルバム情報を付与します。
     */
    private void attachAlbum(final String collectionId, final String collectionName, final String collectionCensoredName, final int collectionPrice, final String collectionViewUrl) {
        album = new AlbumResponse(collectionId, collectionName, collectionCensoredName, collectionPrice, collectionViewUrl);
    }

    /**
     * アルバムアーティスト情報を付与します。
     */
    private void attachAlbumArtist(final int artistId, final String artistName, final String artistViewUrl) {
        Objects.requireNonNull(album, "album must not be null");
        album.attachAlbumArtist(artistId, artistName, artistViewUrl);
    }

    /**
     * 楽曲属性情報を付与します。
     */
    private void attachSongAttribute(final String kind, final String previewUrl, final int discCount, final int discNumber, final boolean isStreamable, final String releaseDate,
        final String artworkUrl30, final String artworkUrl60, final String artworkUrl100, final String collectionExplicitness, final String country, final String currency,
        final String primaryGenreName, final int trackCount,
        final String wrapperType) {
        songAttribute = new SongAttributeResponse(kind, previewUrl, discCount, discNumber, isStreamable, releaseDate, artworkUrl30, artworkUrl60, artworkUrl100, collectionExplicitness, country,
            currency,
            primaryGenreName, trackCount, wrapperType);
    }

    /**
     * ユースケースDto -> レスポンスDtoへの変換を行います。
     */
    public static SongResponse convertTo(final SongUseCaseDto song) {
        final SongResponse dto = new SongResponse();

        dto.attachTrack(song.getTrack().getTrackId(), song.getTrack().getTrackName(), song.getTrack().getTrackCensoredName(), song.getTrack().getTrackViewUrl(), song.getTrack().getTrackPrice(),
            song.getTrack().getTrackExplicitness(), song.getTrack().getTrackNumber(), song.getTrack().getTrackTimeMillis(), song.getTrack().getTrackTimeString());
        dto.attachArtist(song.getArtist().getArtistId(), song.getArtist().getArtistName(), song.getArtist().getArtistViewUrl());
        dto.attachAlbum(song.getAlbum().getCollectionId(), song.getAlbum().getCollectionName(), song.getAlbum().getCollectionCensoredName(), song.getAlbum().getCollectionPrice(),
            song.getAlbum().getCollectionViewUrl());

        song.getAlbum().getAlbumArtist().ifPresent(
            artist -> dto.attachAlbumArtist(artist.getArtistId(), artist.getArtistName(), artist.getArtistViewUrl()));
        dto.attachSongAttribute(song.getSongAttribute().getKind(), song.getSongAttribute().getPreviewUrl(), song.getSongAttribute().getDiscCount(), song.getSongAttribute().getDiscNumber(),
            song.getSongAttribute().isStreamable(), song.getSongAttribute().getReleaseDate(), song.getSongAttribute().getArtworkUrl30(),
            song.getSongAttribute().getArtworkUrl60(), song.getSongAttribute().getArtworkUrl100(), song.getSongAttribute().getCollectionExplicitness(), song.getSongAttribute().getCountry(),
            song.getSongAttribute().getCurrency(), song.getSongAttribute().getPrimaryGenreName(), song.getSongAttribute().getTrackCount(), song.getSongAttribute().getWrapperType());
        return dto;
    }

}
