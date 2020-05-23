package com.vnl.web.usecase.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * アーティスト
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ArtistUseCaseDto {

    /**
     * アーティストID
     */
    private final int artistId;

    /**
     * アーティスト名
     */
    private final String artistName;

    /**
     * アーティストURL
     */
    private final String artistViewUrl;
}
