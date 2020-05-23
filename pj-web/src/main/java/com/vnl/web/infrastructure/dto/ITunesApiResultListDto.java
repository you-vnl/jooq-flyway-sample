package com.vnl.web.infrastructure.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * iTunesAPI結果リスト
 */
@Data
public class ITunesApiResultListDto<T> {

    /**
     * 結果リストのサイズ
     */
    private int resultCount;

    /**
     * 結果リスト
     */
    private List<T> results = new ArrayList<>();
}
