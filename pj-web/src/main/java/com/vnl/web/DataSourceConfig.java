package com.vnl.web;

import javax.sql.DataSource;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * データソースの設定クラスです。MainDBをPrimaryとして接続設定を行います。
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.main")
    public DataSourceProperties datasourceMainProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource datasourceMain(@Qualifier("datasourceMainProperties") final DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager transactionManagerMain(@Qualifier("datasourceMain") final DataSource dataSource1) {
        return new DataSourceTransactionManager(new TransactionAwareDataSourceProxy(dataSource1));
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource(@Qualifier("datasourceMain") final DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean
    public DataSourceConnectionProvider connectionProviderMain(@Qualifier("transactionAwareDataSource") final DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Primary
    @Bean
    public DefaultDSLContext dslContextMain(@Qualifier("configurationMain") final DefaultConfiguration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Primary
    @Bean
    public DefaultConfiguration configurationMain(@Qualifier("connectionProviderMain") final ConnectionProvider connectionProvider) {
        final DefaultConfiguration config = new DefaultConfiguration();
        config.setConnectionProvider(connectionProvider);
        config.setSQLDialect(SQLDialect.POSTGRES);
        return config;
    }
}
