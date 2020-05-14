package com.vnl.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnl.web.infrastructure.dto.ITunesApiResultListDto;
import java.net.URI;
import java.net.http.HttpRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Httpクライアントに関するユーティリティクラスです。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpClientUtils {

    /**
     * GETリクエストパラメータの生成を行います。
     *
     * @param valueMap 検索クエリMap
     * @param endPointPath エンドポイントパス
     * @return リクエストパラメータ
     */
    public static HttpRequest createGetRequest(final MultiValueMap<String, String> valueMap, final URI endPointPath) {
        return HttpRequest.newBuilder().uri(
            UriComponentsBuilder.fromUri(endPointPath).queryParams(valueMap).build().toUri())
            .GET()
            .build();
    }

    /**
     * デシリアライズ用の型を動的に生成してデシリアライズを行います。
     *
     * @param <T> 型
     * @param classType クラス型
     * @param target 対象文字列
     * @return デシリアライズ後のリスト
     */
    public static <T> ITunesApiResultListDto<T> deserialize(final Class<T> classType, final String target) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JavaType type = objectMapper.getTypeFactory().constructParametricType(ITunesApiResultListDto.class, classType);
        return objectMapper.readValue(target, type);
    }

}
