package com.vnl.web.domain.model;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * アルバム
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Album {

    /**
     * アルバムID
     */
    private final String collectionId;

    /**
     * アルバム名
     */
    private final String collectionName;

    /**
     * アルバム名
     */
    private final String collectionCensoredName;

    /**
     * アルバム価格
     */
    private final int collectionPrice;

    /**
     * アルバム閲覧URL
     */
    private final String collectionViewUrl;

    /**
     * アルバムアーティスト
     */
    private Artist albumArtist;

    /**
     * アルバムアーティストを取得します。
     *
     * @return アルバムアーティスト
     */
    public Optional<Artist> getAlbumArtist() {
        return Optional.ofNullable(albumArtist);
    }

    /**
     * アルバムアーティスト情報を付与します。
     *
     * @param artistId アーティストID
     * @param artistName アーティスト名
     * @param artistViewUrl アーティストビューURL
     */
    void attachAlbumArtist(final int artistId, final String artistName, final String artistViewUrl) {
        albumArtist = new Artist(artistId, artistName, artistViewUrl);
    }

}
