package com.vnl.web.domain.model;

import lombok.Value;

/**
 * アーティスト
 */
@Value
class Artist {

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
}
