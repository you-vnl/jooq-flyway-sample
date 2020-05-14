package com.vnl.web.usecase;

import com.vnl.web.domain.model.SearchSongParam;
import com.vnl.web.domain.model.Song;
import com.vnl.web.infrastructure.SongByITunesDataSource;
import com.vnl.web.infrastructure.dto.SongDto;
import java.util.List;
import java.util.function.Consumer;
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

    public static void testConsume(final Consumer<SongDto> consumer) {
        final SongDto songDto = new SongDto();
        songDto.setTrackName("header");
        consumer.accept(songDto);
    }

    /**
     * 楽曲情報リストを取得する.
     *
     * @return 楽曲情報リスト
     */
    public List<Song> getSongDetailList(final String artistName, final int offset) {
        return songByITunesDataSource.findSongsByArtist(artistName, SearchSongParam.SearchSong, offset)
            .stream()
            .collect(Collectors.toUnmodifiableList());
    }

}
