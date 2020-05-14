package com.vnl.web.domain.repository;

import com.vnl.web.domain.model.SearchSongParam;
import com.vnl.web.domain.model.Song;
import java.util.List;

/**
 * 楽曲リポジトリ.
 */
public interface SongRepository {

    /**
     * 楽曲リストを検索して取得する
     *
     * @param name アーティスト名
     * @param searchSongParam 検索区分
     * @param offset 検索オフセット
     * @return 楽曲リスト
     */
    List<Song> findSongsByArtist(String name, SearchSongParam searchSongParam, int offset);

    /**
     * 楽曲を登録する
     */
    void resisterSong();

}
