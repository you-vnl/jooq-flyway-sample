package com.vnl.web;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ストレージの設定クラスです。
 */
@Component
@ConfigurationProperties(prefix = "storage")
@Setter
public class StorageConfig {

    private String path;

    private String apiKey;

    private String secretKey;

    @Getter
    private String bucketName;

    public Path getPath() {
        return Paths.get(path);
    }


    /**
     * Amazon S3のクライアントを作成して返します。(US_EAST_1リージョン)
     *
     * @return S3クライアント
     */
    public AmazonS3 createStorageClient() {
        final AWSCredentials credentials = new BasicAWSCredentials(apiKey, secretKey);
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_1)
            .build();
    }

    /**
     * ストレージオブジェクトを取得します。
     *
     * @param fileName ファイル名
     * @return S3Object
     */
    public S3Object getStorageObject(final String fileName) {
        return createStorageClient().getObject(new GetObjectRequest(getBucketName(), fileName));
    }

}
