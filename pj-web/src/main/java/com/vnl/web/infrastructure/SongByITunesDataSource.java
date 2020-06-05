package com.vnl.web.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnl.web.domain.model.Song;
import com.vnl.web.domain.repository.SongRepository;
import com.vnl.web.domain.type.SearchSongParam;
import com.vnl.web.infrastructure.dto.ITunesApiResultListDto;
import com.vnl.web.infrastructure.dto.SongDto;
import com.vnl.web.util.HttpClientUtils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

/**
 * iTunesAPI連携楽曲データソース
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class SongByITunesDataSource implements SongRepository {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void resisterSong() {
    }

    /**
     * iTunes外部APIを実行して楽曲を検索します。<br>
     *     キャッシュが存在している場合はRedisキャッシュから読み込みを行います。
     *     存在していない場合はDBから取得し、キャッシュに保存します。
     *
     * @param name            アーティスト名
     * @param searchSongParam 検索区分
     * @return 楽曲リスト
     */
    @Override
    public List<Song> findSongsByArtist(final String name, final SearchSongParam searchSongParam, final int offset) {
        final String identifier = "artistName:" + name;

        final List<Song> cashedSongList = readCache(identifier);

        if (!CollectionUtils.isEmpty(cashedSongList)) {
            return cashedSongList;
        }

        final MultiValueMap<String, String> queryParam = searchSongParam.createSongQueryParamMap(name, offset);
        try {
            final HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(HttpClientUtils.createGetRequest(queryParam, searchSongParam.getEndPointUri()), HttpResponse.BodyHandlers.ofString());

            final ITunesApiResultListDto<SongDto> resultList = HttpClientUtils.deserialize(SongDto.class, response.body());
            cacheResult(identifier, resultList.getResults());

            return resultList.getResults()
                    .stream()
                    .map(SongDto::convertToModel)
                    .collect(Collectors.toList());
        } catch (final IOException | InterruptedException e) {
            throw new RuntimeException("API Connection Error.", e);
        }
    }

    /**
     * キャッシュから読み込みを行います。
     *
     * @param identifier 識別子文字列
     * @return 楽曲リスト
     */
    private List<Song> readCache(final String identifier) {
        final List<String> songJson = redisTemplate.opsForList().range(identifier, 0, -1);

        if (songJson == null) {
            return List.of();
        }

         return songJson
                .stream()
                .map(v -> {
                    try {
                        final ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(v, SongDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Redis Read Error.", e);
                    }
                })
                .map(SongDto::convertToModel)
                .collect(Collectors.toList());
    }

    /**
     * 結果をRedisサーバーにキャッシュ保存します。
     * @param identifier 識別文字列
     * @param resultList 結果リスト
     */
    private void cacheResult(final String identifier, final List<SongDto> resultList) {
        final List<String> resultJsonList = resultList
                .stream()
                .map(value -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.writeValueAsString(value);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Redis Save Error.", e);
                    }
                })
                .collect(Collectors.toUnmodifiableList());

        redisTemplate.opsForList().rightPushAll(identifier, resultJsonList);
    }


}
