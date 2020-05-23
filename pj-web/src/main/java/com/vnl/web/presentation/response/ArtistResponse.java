package com.vnl.web.presentation.response;

import lombok.Value;

/**
 * アーティスト
 */
@Value
class ArtistResponse {

    /**
     * アーティストID
     */
    int artistId;

    /**
     * アーティスト名
     */
    String artistName;

    /**
     * アーティストURL
     */
    String artistViewUrl;
}
