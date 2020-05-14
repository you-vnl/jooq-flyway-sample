package com.vnl.web;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger設定クラスです。
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .paths(PathSelectors.regex("^\\/.*"))
            .build()
            .enableUrlTemplating(false)
            .genericModelSubstitutes(Optional.class)
            .useDefaultResponseMessages(false);
    }

    /**
     * APIの基本情報を定義します。
     *
     * @return API基本情報
     */
    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("API Information")
            .description("""
                These are the APIs related to Book and Music.
                These documents are automatically generated by SpringFox.
                """)
            .build();
    }

}
