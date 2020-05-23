package com.vnl.web.infrastructure;

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
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

/**
 * iTunesAPI連携楽曲データソース
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class SongByITunesDataSource implements SongRepository {


    @Override
    public void resisterSong() {
    }

    /**
     * 楽曲検索APIを実行して曲を検索します。
     *
     * @param name アーティスト名
     * @param searchSongParam 検索区分
     * @return 楽曲リスト
     */
    @Override
    public List<Song> findSongsByArtist(final String name, final SearchSongParam searchSongParam, final int offset) {
        final MultiValueMap<String, String> queryParam = searchSongParam.createSongQueryParamMap(name, offset);
        try {
            final HttpResponse<String> response = HttpClient.newHttpClient()
                .send(HttpClientUtils.createGetRequest(queryParam, searchSongParam.getEndPointUri()), HttpResponse.BodyHandlers.ofString());

            final ITunesApiResultListDto<SongDto> resultList = HttpClientUtils.deserialize(SongDto.class, response.body());

            return resultList.getResults()
                .stream()
                .map(SongDto::convertToModel)
                .collect(Collectors.toList());
        } catch (final IOException | InterruptedException e) {
            throw new RuntimeException("API Connection Error.", e);
        }
    }

}
