package com.vnl.web.util;

import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * ファイル操作に関するユーティリティクラスです。
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileOperationUtils {

    /**
     * BeanリストをCSV文字列に変換します。
     *
     * @return CSV文字列
     */
    public static <T> String convertListToCsvString(final List<T> list, final Class<T> classType) {
        final CsvMapper mapper = createCsvMapper();
        final CsvSchema schema = mapper.schemaFor(classType).withHeader();

        try {
            return mapper.writer(schema).writeValueAsString(list);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Csv write Error. Target class: " + classType.toString(), e);
        }
    }

    /**
     * Csvファイルを読み込み、classType型のBeanリストに変換します。
     *
     * @return Beanリスト
     */
    public static <T> List<T> readCsvFileToList(final Path path, final Class<T> classType) {
        final CsvMapper mapper = createCsvMapper();
        final CsvSchema schema = mapper.schemaFor(classType).withHeader();

        try (final BufferedReader br = Files.newBufferedReader(path)) {
            final MappingIterator<T> it = mapper.readerFor(classType).with(schema).readValues(br);
            return it.readAll();
        } catch (final IOException e) {
            throw new RuntimeException("CsvMapper Read Error. Target path: " + path.toString(), e);
        }
    }

    /**
     * Csvマッパーを返します。
     *
     * @return Csvマッパー
     */
    public static CsvMapper createCsvMapper() {
        final CsvMapper mapper = new CsvMapper();
        mapper.registerModule(createJavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * JavaTimeModuleを返します。CsvMapperにおいてLocalDate、LocalDateTime型に対応するように設定を行います。
     *
     * @return JavaTimeModule
     */
    private static JavaTimeModule createJavaTimeModule() {
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")));
        return javaTimeModule;
    }

    /**
     * S3Objectをファイルへ変換します。
     *
     * @param s3Object s3から取得したObject
     * @param file 対象ファイル
     */
    public static void convertS3ObjectToFile(final S3Object s3Object, final File file) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedReader.transferTo(bufferedWriter);
        } catch (final IOException e) {
            throw new RuntimeException("S3Object Convert Error.", e);
        }
    }
}
