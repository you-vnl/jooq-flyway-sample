package com.vnl.web.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * アルバム
 */
@RequiredArgsConstructor
@Getter
class Album {
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
