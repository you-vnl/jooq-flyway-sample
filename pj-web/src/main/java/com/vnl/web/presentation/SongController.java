package com.vnl.web.presentation;

import com.vnl.web.presentation.response.SongResponse;
import com.vnl.web.usecase.SongUseCase;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 楽曲詳細コントローラ
 */
@RestController
@RequiredArgsConstructor
@Validated
public class SongController {

    private final SongUseCase songUseCase;

    private final String MAX_OFFSET_COUNT = "201";

    /**
     * アーティスト楽曲情報取得API
     *
     * @param artistName アーティスト名
     * @return view 表示用ビュー
     */
    @GetMapping("/songs")
    @ApiOperation("アーティスト楽曲検索を行うAPIです。")
    public ResponseEntity<List<SongResponse>> getArtistSongList(
        @Valid @RequestParam(value = "artistName") @NotBlank final String artistName,
        @Valid @RequestParam(value = "offset", defaultValue = MAX_OFFSET_COUNT) @Max(201) final int offset) {
        final List<SongResponse> songResponseList = songUseCase.getSongDetailList(artistName, offset)
            .stream()
            .map(SongResponse::convertTo)
            .collect(Collectors.toUnmodifiableList());

        return new ResponseEntity<>(songResponseList, HttpStatus.CREATED);
    }

    private void validate() {

    }
}
