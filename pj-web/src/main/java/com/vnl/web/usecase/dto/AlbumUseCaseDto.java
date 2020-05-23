package com.vnl.web.usecase.dto;

import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * アルバム
 */
@RequiredArgsConstructor
@Getter
public class AlbumUseCaseDto {
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
    private ArtistUseCaseDto albumArtist;

    /**
     * アルバムアーティストを取得します。
     *
     * @return アルバムアーティスト
     */
    public Optional<ArtistUseCaseDto> getAlbumArtist() {
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
        albumArtist = new ArtistUseCaseDto(artistId, artistName, artistViewUrl);
    }

}
