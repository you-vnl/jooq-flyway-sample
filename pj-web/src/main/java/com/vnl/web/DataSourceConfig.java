package com.vnl.web;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * データソースの設定クラス
 */
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DataSource dataSource;

    @Bean
    public org.jooq.Configuration configuration() {
        final DefaultConfiguration config = new DefaultConfiguration();
        config.setDataSource(dataSource);
        config.setSQLDialect(SQLDialect.POSTGRES);
        config.settings().setRenderSchema(false);
        config.settings().withRenderFormatted(false);
        return config;
    }

    @Bean
    public DSLContext dsl(final org.jooq.Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }
}
