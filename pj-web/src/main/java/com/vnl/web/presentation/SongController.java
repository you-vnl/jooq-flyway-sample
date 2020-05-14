package com.vnl.web.presentation;

import com.vnl.web.domain.model.Song;
import com.vnl.web.infrastructure.dto.SongDto;
import com.vnl.web.usecase.SongUseCase;
import io.swagger.annotations.ApiOperation;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 楽曲詳細コントローラ
 */
@Controller
@RequiredArgsConstructor
public class SongController {

    private final SongUseCase songUseCase;

    /**
     * アーティスト楽曲情報取得API
     *
     * @param artistName アーティスト名
     * @return view 表示用ビュー
     */
    @GetMapping("/songs")
    @ApiOperation("アーティスト楽曲検索を行うAPIです。")
    public ResponseEntity<List<Song>> getArtistSongList(final String artistName, final int offset) {
        return new ResponseEntity<>(songUseCase.getSongDetailList(artistName, offset), HttpStatus.CREATED);
    }

    @PostMapping("/file")
    public void getSongFile(final HttpServletResponse httpResponse) {

        try {
            final FileInputStream fileInputStream = new FileInputStream("test.pdf");

            StreamUtils.copy(fileInputStream, httpResponse.getOutputStream());

            final Consumer<SongDto> c1 = a -> httpResponse.setHeader(a.getTrackName(), a.getCollectionArtistName());

            SongUseCase.testConsume(c1);

        } catch (final IOException e) {
            e.printStackTrace();
        }

    }


}
