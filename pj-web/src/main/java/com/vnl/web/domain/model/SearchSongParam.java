package com.vnl.web.domain.model;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 楽曲検索区分クラス
 */
@RequiredArgsConstructor
public enum SearchSongParam {
    SearchSong("search", "songDataSource"),
    SearchAlbum("search", "album");

    private final String resourceName;
    private final String entity;
    private final String country = "JP";
    private final String lang = "ja_jp";
    private final String limit = "200";


    public URI getEndPointUri() {
        final String url = "https://itunes.apple.com";
        return URI.create(url + "/" + resourceName);
    }

    /**
     * クエリパラメータを格納したMultiValueMapを返します。
     *
     * @param searchTerm 検索する文字列
     * @param offset 検索オフセット
     * @return クエリパラメータを格納したMultiValueMap
     */
    public MultiValueMap<String, String> createSongQueryParamMap(final String searchTerm, final int offset) {
        final MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.addIfAbsent("term", searchTerm);
        valueMap.addIfAbsent("country", country);
        valueMap.addIfAbsent("lang", lang);
        valueMap.addIfAbsent("limit", limit);
        valueMap.addIfAbsent("offset", Integer.toString(offset));
        return valueMap;
    }

}
