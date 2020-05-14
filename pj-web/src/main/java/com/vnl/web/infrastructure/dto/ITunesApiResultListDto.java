package com.vnl.web.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("results")
    private List<T> resultList = new ArrayList<>();
}
