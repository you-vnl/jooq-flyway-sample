package com.vnl.web.usecase;

import com.vnl.web.domain.type.SearchSongParam;
import com.vnl.web.infrastructure.SongByITunesDataSource;
import com.vnl.web.usecase.dto.SongUseCaseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 音楽情報サービスクラス
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SongUseCase {

    private final SongByITunesDataSource songByITunesDataSource;

    /**
     * 楽曲情報リストを取得する.
     *
     * @return 楽曲情報リスト
     */
    public List<SongUseCaseDto> getSongDetailList(final String artistName, final int offset) {
        return songByITunesDataSource.findSongsByArtist(artistName, SearchSongParam.SearchSong, offset)
            .stream()
            .map(SongUseCaseDto::convertToModel)
            .collect(Collectors.toUnmodifiableList());
    }

}
