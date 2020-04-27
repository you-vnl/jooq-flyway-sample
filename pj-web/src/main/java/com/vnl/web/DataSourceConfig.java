package com.vnl.web;

import javax.sql.DataSource;
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
 * データソースの設定クラス
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties datasourcePrimaryProperties() {
        return new DataSourceProperties();
    }

    @Bean("datasourcePrimary")
    @Primary
    public DataSource datasourcePrimary(
        @Qualifier("datasourcePrimaryProperties") final DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = {"txManager1"})
    @Primary
    public PlatformTransactionManager txManager(final DataSource dataSource1) {
        return new DataSourceTransactionManager(new TransactionAwareDataSourceProxy(dataSource1));
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(transactionAwareDataSource());
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSource() {
        return new TransactionAwareDataSourceProxy(datasourcePrimary(datasourcePrimaryProperties()));
    }

    @Bean
    public DefaultDSLContext dsl(final org.jooq.Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Bean
    public org.jooq.Configuration configuration() {
        final DefaultConfiguration config = new DefaultConfiguration();
        config.setConnectionProvider(connectionProvider());
        config.setSQLDialect(SQLDialect.POSTGRES);
        return config;
    }
}
